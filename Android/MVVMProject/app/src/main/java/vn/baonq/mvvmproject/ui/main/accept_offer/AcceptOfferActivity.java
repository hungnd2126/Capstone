package vn.baonq.mvvmproject.ui.main.accept_offer;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.RequestedVMResponse;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.ActivityAcceptOfferBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;


public class AcceptOfferActivity extends BaseActivity<ActivityAcceptOfferBinding, AcceptOfferViewModel> implements AcceptOfferNavigator {
    public static final String TAG = AcceptOfferActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AcceptOfferActivity.class);
        return intent;
    }

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProvider.Factory vmFactory;
    @Inject
    AcceptOfferAdapter acceptOfferAdapter;

    AcceptOfferViewModel acceptOfferViewModel;

    private ActivityAcceptOfferBinding activityAcceptOfferBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_accept_offer;
    }

    @Override
    public AcceptOfferViewModel getViewModel() {
        acceptOfferViewModel = ViewModelProviders.of(this, vmFactory).get(AcceptOfferViewModel.class);
        return acceptOfferViewModel;
    }

    PostViewModel post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        activityAcceptOfferBinding = getViewDataBinding();
        acceptOfferViewModel.setNavigator(this);
        ProcessBar.runProgress(this);
        receiveIntent();
        String  senderId= acceptOfferViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= acceptOfferViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= acceptOfferViewModel.getDataManager().getCurrentUser().getName();
        acceptOfferAdapter.addSender(senderId, senderAvartar.replace(ApiEndPoint.BASE_URL,""), senderName);
        acceptOfferAdapter.setPost(post);
        setLiveData();
        setUp();
        ProcessBar.endProgress();
    }

    @Override
    public void onViewDetailClick() {
        Intent intent = DetailActivity.newIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("callFrom", "acceptofferActivity");
        intent.putExtra("postId", post.getId());
        startActivity(intent);
    }

    @Override
    public void changeOffer(int i) {
        if (i == 2){ // DA GUI
            activityAcceptOfferBinding.txtDagui.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            activityAcceptOfferBinding.txtNhanmua.setBackgroundColor(getResources().getColor(R.color.gray));
            acceptOfferViewModel.getAcceptOfferLiveData().observe(this, requested -> acceptOfferViewModel.addAcceptOfferItem(requested, 2));
        }else{ // NHANMUA
            activityAcceptOfferBinding.txtDagui.setBackgroundColor(getResources().getColor(R.color.gray));
            activityAcceptOfferBinding.txtNhanmua.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            acceptOfferViewModel.getAcceptOfferLiveData().observe(this, requested -> acceptOfferViewModel.addAcceptOfferItem(requested, 1));
        }
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityAcceptOfferBinding.acceptOfferRecyclerView.setLayoutManager(mLayoutManager);
        activityAcceptOfferBinding.acceptOfferRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityAcceptOfferBinding.acceptOfferRecyclerView.setAdapter(acceptOfferAdapter);
    }

    private void setLiveData() {
        acceptOfferViewModel.setProductData(post.getProductName(),
                BASE_URL + post.getImageUrl(),
                post.getBuy_Address().toString(),
                post.getDelivery_Address().toString());
        acceptOfferViewModel.setRequested(post.getId());
        changeOffer(1);
    }

    private void receiveIntent() {
        Bundle bundle = getIntent().getBundleExtra("Post");
        post = (PostViewModel) bundle.getSerializable("Post");
    }


}
