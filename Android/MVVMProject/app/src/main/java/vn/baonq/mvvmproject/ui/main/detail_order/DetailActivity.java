package vn.baonq.mvvmproject.ui.main.detail_order;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.ActivityDetailOrderBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;


public class DetailActivity extends BaseActivity<ActivityDetailOrderBinding, DetailViewModel> implements DetailNavigator {

    @Inject
    ViewModelProvider.Factory vmFactory;
    @Inject
    DetailOfferItemAdapter DetailOfferItemAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    private ActivityDetailOrderBinding binding;
    DetailViewModel detailViewModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_order;
    }

    @Override
    public DetailViewModel getViewModel() {
        detailViewModel = ViewModelProviders.of(this, vmFactory).get(DetailViewModel.class);
        return detailViewModel;
    }

    @Override
    public void sendMessage() {
        String  senderId= detailViewModel.getDataManager().getCurrentUser().getUid();
        String senderAvartar= detailViewModel.getDataManager().getCurrentUser().getImageUrl();
        String senderName= detailViewModel.getDataManager().getCurrentUser().getName();

        Intent intent = new Intent(this, CustomHolderMessagesActivity.class);
        intent.putExtra("receiverId", post.getUserId());
        intent.putExtra("receiverName",post.getNickname());
        intent.putExtra("receiverAvatar", post.getUserAvartar());
        intent.putExtra("SenderId", senderId);
        intent.putExtra("Avatar", senderAvartar);
        intent.putExtra("NickName", senderName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        detailViewModel.setNavigator(this);
        binding = getViewDataBinding();
        setUp();
        receiveIntent();
    }

    @Override
    public void onClickPay() {
        Intent intent = new Intent(this, DeliveryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Post", post);
        intent.putExtra("data", bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    PostViewModel post;

    public void receiveIntent() {
        String callForm = getIntent().getStringExtra("callFrom");

        if (callForm != null && (callForm.equals("homefragment") || callForm.equals("STM"))) {
            Bundle bundle = getIntent().getBundleExtra("data");
            post = (PostViewModel) bundle.getSerializable("Post");
            if (post.getDomain() != null && !post.getDomain().equals("")) {
                binding.domain.setVisibility(View.VISIBLE);
                binding.url.setVisibility(View.VISIBLE);
            }
            detailViewModel.updateDetailViewModel(post);
            detailViewModel.setRequested(post.getId());
            binding.textView15.setVisibility(View.VISIBLE);
            if (callForm.equals("homefragment")) {
                binding.actDetailOrderBtnOffer.setVisibility(View.VISIBLE);
            } else {
                binding.actDetailOrderBtnOffer.setVisibility(View.GONE);
            }
            setLiveData();
        } else if (callForm != null && (callForm.equals("acceptofferActivity") || callForm.equals("notification"))) {
            ProcessBar.runProgress(this);
            detailViewModel.getPostDetail(getIntent().getIntExtra("postId", 1), binding);
            binding.textView15.setVisibility(View.GONE);
            binding.actDetailOrderBtnOffer.setVisibility(View.GONE);
        }
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.acceptOfferRecyclerView.setLayoutManager(mLayoutManager);
        binding.acceptOfferRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.acceptOfferRecyclerView.setAdapter(DetailOfferItemAdapter);
    }

    private void setLiveData() {
        ProcessBar.runProgress(this);
        detailViewModel.getAcceptOfferLiveData().observe(this, requested -> detailViewModel.addAcceptOfferItem(requested));
    }

}
