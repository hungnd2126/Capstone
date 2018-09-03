package vn.baonq.mvvmproject.ui.main.time_line;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.lang.UCharacter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import net.alexandroid.utils.toaster.Toaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;
import vn.baonq.mvvmproject.databinding.ActivityTimelineBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class TimeLineActivity extends BaseActivity<ActivityTimelineBinding, TimeLineViewModel>
        implements LocationListener, TimeLineNavigator, Toaster.DialogCallback {
    LocationManager locationManager;
    @Inject
    TimeLineAdapter timeLineAdapter;

    private TimeLineViewModel timeLineViewModel;

    ActivityTimelineBinding binding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_timeline;
    }

    @Override
    public TimeLineViewModel getViewModel() {
        timeLineViewModel = ViewModelProviders.of(this, vmFactory).get(TimeLineViewModel.class);
        return timeLineViewModel;
    }
    HashMap<String,Integer> spin= new HashMap<String,Integer>();
    String city="";
    String country="";
    double latitude;
    double longitude;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        binding = getViewDataBinding();
        timeLineViewModel.setNavigator(this);
        receiveIntent();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET}
                    , 10);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        getCurrentPosition();
        timeLineAdapter.addPost(Post);
        timeLineViewModel.setBinding(binding);
        timeLineViewModel.setShowTracking(Showtracking );
        if (Showtracking){
            binding.tracking.setVisibility(View.VISIBLE);
        }else{
            binding.tracking.setVisibility(View.GONE);
        }
        setUp();
        setDataSpinner();

    }
    @Override
    public void onResume() {
        super.onResume();
        setLiveData();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                            , 10);
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                break;
            default:
                break;
        }
    }
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.timelineRecyclerView.setLayoutManager(mLayoutManager);
        binding.timelineRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.timelineRecyclerView.setAdapter(timeLineAdapter);
   }

    private void setLiveData() {
        timeLineViewModel.getTimeline(Post.getOrderId());
        timeLineViewModel.getTimelineLiveData().observe(this, requested -> timeLineViewModel.addTimeLineItem(requested));
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude= location.getLatitude();
        double longitude= location.getLongitude();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  = addresses.get(0).getLocality();
           city  = addresses.get(0).getAdminArea();
         country = addresses.get(0).getCountryName();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);
    }

    @Override
    public void updateTimeline() {
        int status=binding.spinner.getSelectedItemPosition();

        switch (status){
            case 0:
                    timeLineViewModel.getCompositeDisposable().add(timeLineViewModel.getDataManager()
                            .updateTimeline(Post.getOrderId(), 1,city+", "+country,longitude,latitude)
                            .subscribeOn(timeLineViewModel.getSchedulerProvider().io())
                            .observeOn(timeLineViewModel.getSchedulerProvider().ui())
                            .subscribe(response -> {
                                if (response != null) {
                                    List<TimelineViewModel> list= timeLineViewModel.listMutableLiveData.getValue();
                                    if(list!=null){
                                        list.add(response);
                                        timeLineViewModel.listMutableLiveData.setValue(list);}
                                    else{
                                        list= new ArrayList<>();
                                        list.add(response);
                                        timeLineViewModel.listMutableLiveData.setValue(list);
                                    }
                                    ProcessBar.endProgress();
                                    Toasty.success(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {
                                Toasty.success(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                                Log.d("updateTimeline : Error", throwable.getMessage());
                                ProcessBar.endProgress();
                            }));
                break;
            case 4:
                showDialog();
                break;
            default:
                ProcessBar.runProgress(this);
                timeLineViewModel.getCompositeDisposable().add(timeLineViewModel.getDataManager()
                        .updateTimeline(Post.getOrderId(), status+1,"",0,0)
                        .subscribeOn(timeLineViewModel.getSchedulerProvider().io())
                        .observeOn(timeLineViewModel.getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null) {
                                List<TimelineViewModel> list= timeLineViewModel.listMutableLiveData.getValue();
                                if(list!=null){
                                    list.add(response);
                                    timeLineViewModel.listMutableLiveData.setValue(list);}
                                else{
                                    list= new ArrayList<>();
                                    list.add(response);
                                    timeLineViewModel.listMutableLiveData.setValue(list);
                                }
                                ProcessBar.endProgress();
                                Toasty.success(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            Toasty.success(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            Log.d("updateTimeline : Error", throwable.getMessage());
                            ProcessBar.endProgress();
                        }));

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }
    ArrayAdapter<String> adapter;
    private  void setDataSpinner(){
       ArrayList<String> dataSpinner= new  ArrayList<String>();

           dataSpinner.add("Đang ở "+city+", "+country);
           spin.put("Đang ở "+city+", "+country,1);

           dataSpinner.add("Đã mua được hàng");
           spin.put("Đã mua được hàng",2);

           dataSpinner.add("Có thể giao hàng");
           spin.put("Có thể giao hàng",3);

           dataSpinner.add("Không thể mua hàng");
           spin.put("Không thể mua hàng",4);

           dataSpinner.add("Hủy đơn hàng");
           spin.put("Hủy đơn hàng",5);

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, dataSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }
    @Override
    public void onPositiveClick() {
        ProcessBar.runProgress(this);
        timeLineViewModel.getCompositeDisposable().add(timeLineViewModel.getDataManager()
                .updateTimeline(Post.getOrderId(), 5,"",0,0)
                .subscribeOn(timeLineViewModel.getSchedulerProvider().io())
                .observeOn(timeLineViewModel.getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        timeLineAdapter.addItem(response);
                        ProcessBar.endProgress();
                        binding.tracking.setVisibility(View.GONE);
                        Toasty.success(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toasty.success(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("updateTimeline : Error", throwable.getMessage());
                    ProcessBar.endProgress();
                }));
    }

    @Override
    public void onNegativeClick() {
        Log.d("TAG", "Negative");
    }

    @Override
    public void onOutOfTheBoundClick() {

    }

    private Toaster mToaster;
    private void showDialog() {
        mToaster = new Toaster.Builder(this)
                .setTitle("Hủy đơn hàng")
                .setText("Đơn hàng của bạn sẽ bị hủy và time line sẽ kết thúc")
                .setPositive("Đồng ý")
                .setNegative("Thoát")
                .setAnimationDuration(300)
                .setCallBack(this).build();
        mToaster.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                    , 10);
            return;
        }


        try {
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            latitude  = myLocation.getLatitude();
            longitude = myLocation.getLongitude();

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            city  = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();

        } catch (IOException e) {
            Toast.makeText(this, "Có lỗi xảy ra. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
        }


    }
    PostViewModel Post;
    boolean Showtracking= false;
    private void receiveIntent() {
        Bundle bundle = getIntent().getBundleExtra("Data");
        Post = (PostViewModel) bundle.getSerializable("Post");
        Showtracking= getIntent().getBooleanExtra("Showtracking", false);
    }
}
