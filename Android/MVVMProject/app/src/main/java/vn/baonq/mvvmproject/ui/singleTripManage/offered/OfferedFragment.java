package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.databinding.FragmentOfferedBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;

public class OfferedFragment extends BaseFragment<FragmentOfferedBinding, OfferedViewModel> {

    public static final String TAG = OfferedFragment.class.getSimpleName();
    private static GetTripByUserResponse.TripItemResponse trip;

    public static void setTrip(GetTripByUserResponse.TripItemResponse  trip) {
        OfferedFragment.trip= trip;
    }

    public static OfferedFragment newInstance(GetTripByUserResponse.TripItemResponse trip) {

        Bundle args = new Bundle();
        OfferedFragment fragment = new OfferedFragment();
        fragment.setArguments(args);
        setTrip(trip);
        return fragment;
    }

    OfferedViewModel mOfferedViewModel;
    FragmentOfferedBinding fragmentOfferedBinding;

    @Inject
    OfferedAdapter offeredAdapter;

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
        return R.layout.fragment_offered;
    }

    @Override
    public OfferedViewModel getViewModel() {
        mOfferedViewModel = ViewModelProviders.of(this, vmFactory).get(OfferedViewModel.class);
        return mOfferedViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOfferedBinding = getViewDataBinding();
        setUp();
        offeredAdapter.addTrip(trip);
      //  setLiveData();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentOfferedBinding.fragmentOfferedRecyclerView.setLayoutManager(mLayoutManager);
        fragmentOfferedBinding.fragmentOfferedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offeredAdapter.setViewModel(mOfferedViewModel);
        fragmentOfferedBinding.fragmentOfferedRecyclerView.setAdapter(offeredAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        setLiveData();
    }

    private void setLiveData() {
        mOfferedViewModel.getData(trip.getTripId(), getContext());
        mOfferedViewModel.getPostLiveData().observe(this, data -> mOfferedViewModel.addPosts(data));
    }

}
