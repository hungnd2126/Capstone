package vn.baonq.mvvmproject.ui.main.order.Transit;

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
import vn.baonq.mvvmproject.databinding.FragmentTransitBinding;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class TransitFragment extends BaseFragment<FragmentTransitBinding, TransitViewModel> implements TransitNavigator {
    public static final String TAG = TransitFragment.class.getSimpleName();

    @Inject
    TransitAdapter transitAdapter;

    private TransitViewModel transitViewModel;

    FragmentTransitBinding fragmentTransitBinding;
    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    public static TransitFragment newInstance(){
        Bundle args = new Bundle();

        TransitFragment fragment = new TransitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_transit;
    }

    @Override
    public TransitViewModel getViewModel(){
        transitViewModel = ViewModelProviders.of(this, vmFactory).get(TransitViewModel.class);
        return transitViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        transitViewModel.setNavigator(this);
        Log.d(TAG,"on create");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fragmentTransitBinding = getViewDataBinding();

    }

    @Override
    public void onResume() {
        super.onResume();
        ProcessBar.runProgress(getContext());
        String  senderId= transitViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= transitViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= transitViewModel.getDataManager().getCurrentUser().getName();
        transitAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        setUp();
        setLiveData();
        ProcessBar.endProgress();
    }

    private void setUp(){
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentTransitBinding.requestedRecyclerView.setLayoutManager(mLayoutManager);
        fragmentTransitBinding.requestedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentTransitBinding.requestedRecyclerView.setAdapter(transitAdapter);
    }

    public void setLiveData(){
        transitViewModel.fetchTransit();
        transitViewModel.getPostLiveData().observe(this, transit -> transitViewModel.addTransitItem(transit));
    }
}
