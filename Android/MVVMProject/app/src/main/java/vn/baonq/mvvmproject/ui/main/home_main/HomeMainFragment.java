package vn.baonq.mvvmproject.ui.main.home_main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.databinding.FragmentHomeMainBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;

public class HomeMainFragment extends BaseFragment<FragmentHomeMainBinding, HomeMainViewModel> {
    public static final String TAG = HomeMainFragment.class.getSimpleName();

    private FragmentHomeMainBinding fragmentHomeMainBinding;
    @Inject
    HomeMainPagerAdapter homeMainPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private HomeMainViewModel homeMainViewModel;

    public static HomeMainFragment newInstance() {

        Bundle args = new Bundle();

        HomeMainFragment fragment = new HomeMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_main;
    }

    @Override
    public HomeMainViewModel getViewModel() {
        homeMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeMainViewModel.class);
        return homeMainViewModel;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Create");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentHomeMainBinding = getViewDataBinding();
        initUI();
    }

    private void initUI(){
        homeMainPagerAdapter.setCount(2);

        viewPager = fragmentHomeMainBinding.homeMainViewPager;
        viewPager.setAdapter(homeMainPagerAdapter);

        tabLayout = fragmentHomeMainBinding.homeMainTabs;
        //buyer
        tabLayout.addTab(tabLayout.newTab().setText("Bạn đi du lịch"));

        //traveler
        tabLayout.addTab(tabLayout.newTab().setText("Bạn muốn mua"));

        //viewPager.setOffscreenPageLimit(3);

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
}
