package vn.baonq.mvvmproject.ui.main.order;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityMainBinding;
import vn.baonq.mvvmproject.databinding.FragmentOrderBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;

public class OrderFragment extends BaseFragment<FragmentOrderBinding, OrderViewModel> implements OrderNavigator {

    private FragmentOrderBinding fragmentOrderBinding;
    @Inject
    OrderPagerAdapter orderPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private OrderViewModel orderViewModel;

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public OrderViewModel getViewModel() {
        orderViewModel = ViewModelProviders.of(this, mViewModelFactory).get(OrderViewModel.class);
        return orderViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel.setNavigator(this);
        Log.d("OrderFragment", "Create");

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOrderBinding = getViewDataBinding();


        initUI();
    }

    private void initUI() {
        orderPagerAdapter.setCount(3);

        viewPager = fragmentOrderBinding.viewPager;
        viewPager.setAdapter(orderPagerAdapter);
        tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Đã đăng"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang vận chuyển"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã nhận"));
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
}
