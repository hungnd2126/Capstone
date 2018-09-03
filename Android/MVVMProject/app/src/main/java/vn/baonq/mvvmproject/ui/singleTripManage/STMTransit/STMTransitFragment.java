package vn.baonq.mvvmproject.ui.singleTripManage.STMTransit;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.FragmentStmtransitBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.singleTripManage.offered.OfferedFragment;

public class STMTransitFragment extends BaseFragment<FragmentStmtransitBinding, STMTransitViewModel > implements STMTransitNavigator{

    public static final String TAG = STMTransitFragment.class.getSimpleName();
    @Inject
    STMTransitAdapter stmTransitAdapter;

    private STMTransitViewModel stmTransitViewModel;

    public static int tripId;

    public static void setTripId(int tripId) {
        STMTransitFragment.tripId = tripId;
    }

    FragmentStmtransitBinding fragmentSTMTransitBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    public static STMTransitFragment newInstance(int tripId) {
        Bundle args = new Bundle();

        STMTransitFragment fragment = new STMTransitFragment();
        fragment.setArguments(args);
        setTripId(tripId);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stmtransit;
    }

    @Override
    public STMTransitViewModel getViewModel() {
        stmTransitViewModel = ViewModelProviders.of(this,vmFactory).get(STMTransitViewModel.class);
        return stmTransitViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        stmTransitViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fragmentSTMTransitBinding = getViewDataBinding();
        Log.d(TAG, "on view created");
        String  senderId= stmTransitViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= stmTransitViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= stmTransitViewModel.getDataManager().getCurrentUser().getName();
        stmTransitAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        setUp();
        setLiveData();
    }

    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentSTMTransitBinding.stmTransitRecyclerView.setLayoutManager(mLayoutManager);
        fragmentSTMTransitBinding.stmTransitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentSTMTransitBinding.stmTransitRecyclerView.setAdapter(stmTransitAdapter);
    }

    public void setLiveData(){
        stmTransitViewModel.fetchTransit(tripId);
        stmTransitViewModel.getPostLiveData().observe(this, transit -> stmTransitViewModel.addSTMTransitItem(transit));
    }
}
