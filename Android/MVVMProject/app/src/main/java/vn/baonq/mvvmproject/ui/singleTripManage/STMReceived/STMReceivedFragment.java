package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

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
import vn.baonq.mvvmproject.databinding.FragmentStmreceivedBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;

public class STMReceivedFragment extends BaseFragment<FragmentStmreceivedBinding, STMReceivedViewModel > implements STMReceivedNavigator{
    public static final String TAG = STMReceivedFragment.class.getSimpleName();
    @Inject
    STMReceivedAdapter stmReceivedAdapter;

    private STMReceivedViewModel stmReceivedViewModel;

    FragmentStmreceivedBinding fragmentSTMReceivedBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;
    public static int tripId;
    public static STMReceivedFragment newInstance(int tripId) {
        Bundle args = new Bundle();
        STMReceivedFragment.tripId= tripId;
        STMReceivedFragment fragment = new STMReceivedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stmreceived;
    }

    @Override
    public STMReceivedViewModel getViewModel() {
        stmReceivedViewModel = ViewModelProviders.of(this,vmFactory).get(STMReceivedViewModel.class);
        return stmReceivedViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        stmReceivedViewModel.setNavigator(this);
        Log.d(TAG, "on create");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fragmentSTMReceivedBinding = getViewDataBinding();
        String  senderId= stmReceivedViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= stmReceivedViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= stmReceivedViewModel.getDataManager().getCurrentUser().getName();
        stmReceivedAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        setUp();
        setLiveData();
    }

    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentSTMReceivedBinding.stmReceivedRecyclerView.setLayoutManager(mLayoutManager);
        fragmentSTMReceivedBinding.stmReceivedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentSTMReceivedBinding.stmReceivedRecyclerView.setAdapter(stmReceivedAdapter);
    }

    public void setLiveData(){
        stmReceivedViewModel.fetchReceived(tripId);
        stmReceivedViewModel.getPostLiveData().observe(this, received -> stmReceivedViewModel.addSTMReceivedItem(received));
    }
}
