package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.home.Post.PostAdapter;
import vn.baonq.mvvmproject.ui.main.home.Post.PostViewModel;
import vn.baonq.mvvmproject.databinding.FragmentOfferingBinding;
import vn.baonq.mvvmproject.ui.singleTripManage.offered.OfferedFragment;

public class OfferingFragment extends BaseFragment<FragmentOfferingBinding, OfferingViewModel>{

    public static final String TAG = OfferingFragment.class.getSimpleName();
    private static GetTripByUserResponse.TripItemResponse trip;

    public static void setTrip(GetTripByUserResponse.TripItemResponse trip) {
        OfferingFragment.trip = trip;
    }
    public static OfferingFragment newInstance(GetTripByUserResponse.TripItemResponse trip) {

        Bundle args = new Bundle();
        OfferingFragment fragment = new OfferingFragment();
        fragment.setArguments(args);
        setTrip(trip);
        return fragment;
    }

    OfferingViewModel mOfferingViewModel;
    FragmentOfferingBinding fragmentOfferingBinding;

    @Inject
    OfferingAdapter offeringAdapter;

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
        return R.layout.fragment_offering;
    }

    @Override
    public OfferingViewModel getViewModel() {
        mOfferingViewModel = ViewModelProviders.of(this, vmFactory).get(OfferingViewModel.class);
        return mOfferingViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOfferingBinding = getViewDataBinding();
        setUp();
        setLiveData();
      //  this.hideKeyboard();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentOfferingBinding.fragmentOfferingRecyclerView.setLayoutManager(mLayoutManager);
        fragmentOfferingBinding.fragmentOfferingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentOfferingBinding.fragmentOfferingRecyclerView.setAdapter(offeringAdapter);

    }

    private void setLiveData() {
        mOfferingViewModel.getData(trip.getTripId());
        mOfferingViewModel.getPostLiveData().observe(this, offering -> mOfferingViewModel.addPosts(offering));
    }

}
