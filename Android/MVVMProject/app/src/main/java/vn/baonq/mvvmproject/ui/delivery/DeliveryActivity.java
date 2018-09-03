package vn.baonq.mvvmproject.ui.delivery;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.databinding.FragmentDeliveryBinding;
import vn.baonq.mvvmproject.ui.addtrip.AddTripActivity;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.base.BaseFragment;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryAdapter;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryNavigator;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class DeliveryActivity extends BaseActivity<FragmentDeliveryBinding, DeliveryViewModel> implements DeliveryNavigator {
    public static final String TAG = DeliveryActivity.class.getSimpleName();

    @Inject
    DeliveryAdapter deliveryAdapter;

    FragmentDeliveryBinding fragmentDeliveryBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    ViewModelProvider.Factory vmFactory;

    private DeliveryViewModel deliveryViewModel;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, AddTripActivity.class);
        return intent;
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
    PostViewModel postViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryViewModel.setNavigator(this);
        ProcessBar.runProgress(this);
        getSupportActionBar().show();
        receiveData();
        fragmentDeliveryBinding = getViewDataBinding();
        setUp();
        setLiveData();
        deliveryAdapter.addPost(postViewModel);
        ProcessBar.endProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUp() {
        deliveryAdapter.setCallFrom("activity");
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentDeliveryBinding.deliveredRecyclerView.setLayoutManager(mLayoutManager);
        fragmentDeliveryBinding.deliveredRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentDeliveryBinding.deliveredRecyclerView.setAdapter(deliveryAdapter);

    }

    private void setLiveData() {
        deliveryViewModel.setRequested(postViewModel.getId());
        deliveryViewModel.getPostLiveData().observe(this, requested -> deliveryViewModel.addRequestedItem(requested));
    }


    @Override
    public void onAddTripClick() {
        Intent intent = AddTripActivity.newIntent(this);
        startActivity(intent);
    }
    private void receiveData(){
        Bundle bundle= getIntent().getBundleExtra("data");
        postViewModel=(PostViewModel) bundle.getSerializable("Post");
    }
}