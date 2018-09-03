package vn.baonq.mvvmproject.ui.main.order.Requested;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.databinding.ActivityMainBinding;
import vn.baonq.mvvmproject.databinding.FragmentRequestedBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class RequestedFragment extends BaseFragment<FragmentRequestedBinding, RequestedViewModel> implements RequestedNavigator {
    public static final String TAG = RequestedFragment.class.getSimpleName();
    @Inject
    RequestedAdapter requestedAdapter;

    private RequestedViewModel requestedViewModel;

    FragmentRequestedBinding fragmentRequestedBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;


    public static RequestedFragment newInstance() {
        Bundle args = new Bundle();

        RequestedFragment fragment = new RequestedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_requested;
    }

    @Override
    public RequestedViewModel getViewModel() {
        requestedViewModel = ViewModelProviders.of(this, vmFactory).get(RequestedViewModel.class);
        return requestedViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestedViewModel.setNavigator(this);

        fragmentRequestedBinding = getViewDataBinding();
    }

    @Override
    public void onResume() {
        super.onResume();
        ProcessBar.runProgress(getContext());
        setUp();
        setLiveData();
        ProcessBar.endProgress();
    }

    private void setUp() {
        requestedAdapter.setViewModel(requestedViewModel);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentRequestedBinding.requestedRecyclerView.setLayoutManager(mLayoutManager);
        fragmentRequestedBinding.requestedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentRequestedBinding.requestedRecyclerView.setAdapter(requestedAdapter);
    }

    public void setLiveData() {
        requestedViewModel.fetchRequested();
        requestedViewModel.getPostLiveData().observe(this, requested -> requestedViewModel.addRequestedItem(requested));
    }
}
