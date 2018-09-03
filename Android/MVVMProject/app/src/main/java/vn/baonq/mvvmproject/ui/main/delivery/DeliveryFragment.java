package vn.baonq.mvvmproject.ui.main.delivery;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.FragmentDeliveryBinding;
import vn.baonq.mvvmproject.ui.addtrip.AddTripActivity;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class DeliveryFragment extends BaseFragment<FragmentDeliveryBinding, DeliveryViewModel> implements DeliveryNavigator {
    public static final String TAG = DeliveryFragment.class.getSimpleName();

    @Inject
    DeliveryAdapter deliveryAdapter;

    FragmentDeliveryBinding fragmentDeliveryBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    private DeliveryViewModel deliveryViewModel;

    public static DeliveryFragment newInstance() {

        Bundle args = new Bundle();
        DeliveryFragment fragment = new DeliveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_delivery;
    }

    @Override
    public DeliveryViewModel getViewModel() {
        deliveryViewModel = ViewModelProviders.of(this, vmFactory).get(DeliveryViewModel.class);
        return deliveryViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryViewModel.setNavigator(this);
        ProcessBar.runProgress(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDeliveryBinding = getViewDataBinding();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUp();
        setLiveData();
        ProcessBar.endProgress();
    }

    private void setUp(){
        deliveryAdapter.setCallFrom("fragment");
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentDeliveryBinding.deliveredRecyclerView.setLayoutManager(mLayoutManager);
        fragmentDeliveryBinding.deliveredRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentDeliveryBinding.deliveredRecyclerView.setAdapter(deliveryAdapter);
    }

    private void setLiveData(){
        deliveryViewModel.setRequested(0);
        deliveryViewModel.getPostLiveData().observe(this, requested -> deliveryViewModel.addRequestedItem(requested));
    }


    @Override
    public void onAddTripClick() {
        Intent intent = AddTripActivity.newIntent(this.getActivity());
        this.getActivity().startActivity(intent);
    }
}
