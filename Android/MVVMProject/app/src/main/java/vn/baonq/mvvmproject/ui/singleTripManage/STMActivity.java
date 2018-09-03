package vn.baonq.mvvmproject.ui.singleTripManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.databinding.ActivitySingleTripManageBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class STMActivity extends BaseActivity<ActivitySingleTripManageBinding, STMViewModel> implements STMNavigator, HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private ActivitySingleTripManageBinding binding;
    @Inject
    STMViewModel stmViewModel;
    @Inject
    STMPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, STMActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_trip_manage;
    }

    @Override
    public STMViewModel getViewModel() {
        return stmViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        getSupportActionBar().show();
        initUI();

    }

    private void initUI(){
        Bundle bundle = getIntent().getBundleExtra("data");
        GetTripByUserResponse.TripItemResponse trip = (GetTripByUserResponse.TripItemResponse) bundle.getSerializable("Trip");
        binding.tripTitle.setText(trip.getName());
        binding.tripMoney.setText(CommonUtils.formatPrice(trip.getEarning()));
        adapter.setCount(4);
        viewPager = binding.viewPager;
        adapter.setTrip(trip);
        viewPager.setAdapter(adapter);

        tabLayout = binding.singleTripManageTab;
        tabLayout.addTab(tabLayout.newTab().setText("Đã nhận mua"));
        tabLayout.addTab(tabLayout.newTab().setText("Được nhờ mua"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang vận chuyển"));
        tabLayout.addTab(tabLayout.newTab().setText("Hoàn thành"));

        viewPager.setOffscreenPageLimit(1);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
