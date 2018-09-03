package vn.baonq.mvvmproject.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;
import vn.baonq.mvvmproject.databinding.ActivityMapBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends BaseActivity<ActivityMapBinding,MapViewModel>  implements OnMapReadyCallback {

    @Inject
    MapViewModel mapViewModel;

    ActivityMapBinding activityMapBinding;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public MapViewModel getViewModel() {
        return mapViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        ProcessBar.runProgress(this);
        receiveIntent();
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ProcessBar.endProgress();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng position = new LatLng(model.getLatitude(), model.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10f));
    }
    TimelineViewModel model;
    private void receiveIntent(){
        Bundle bundle= getIntent().getBundleExtra("Data");
        model= (TimelineViewModel) bundle.getSerializable("Model");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        finish();
        return true;
    }
}
