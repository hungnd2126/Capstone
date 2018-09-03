package vn.baonq.mvvmproject.ui.main.order.Received;

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
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.FragmentReceivedBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class ReceivedFragment extends BaseFragment<FragmentReceivedBinding, ReceivedViewModel > implements ReceivedNavigator{
    public static final String TAG = ReceivedFragment.class.getSimpleName();
    @Inject
    ReceivedAdapter receivedAdapter;

    private ReceivedViewModel receivedViewModel;

    FragmentReceivedBinding fragmentReceivedBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    public static ReceivedFragment newInstance() {
        Bundle args = new Bundle();

        ReceivedFragment fragment = new ReceivedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_received;
    }

    @Override
    public ReceivedViewModel getViewModel() {
        receivedViewModel = ViewModelProviders.of(this,vmFactory).get(ReceivedViewModel.class);
        return receivedViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        receivedViewModel.setNavigator(this);
        Log.d(TAG, "on create");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fragmentReceivedBinding = getViewDataBinding();
    }

    @Override
    public void onResume() {
        super.onResume();
        ProcessBar.runProgress(getContext());
        String  senderId= receivedViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= receivedViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= receivedViewModel.getDataManager().getCurrentUser().getName();
        receivedAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        setUp();
        setLiveData();
        ProcessBar.endProgress();

    }

    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentReceivedBinding.receivedRecyclerView.setLayoutManager(mLayoutManager);
        fragmentReceivedBinding.receivedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentReceivedBinding.receivedRecyclerView.setAdapter(receivedAdapter);
    }

    public void setLiveData(){
        receivedViewModel.fetchReceived();
        receivedViewModel.getPostLiveData().observe(this, received -> receivedViewModel.addReceivedItem(received));
    }
}
