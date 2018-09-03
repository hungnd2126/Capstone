package vn.baonq.mvvmproject.ui.complete_offer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.ActivityCompleteOfferBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;
import vn.baonq.mvvmproject.ui.main.time_line.TimeLineActivity;
import vn.baonq.mvvmproject.ui.rating.RatingDialog;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class CompleteOfferActivity extends BaseActivity<ActivityCompleteOfferBinding, CompleteOfferViewModel> implements CompleteOfferNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    public static final String TAG = CompleteOfferActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, CompleteOfferActivity.class);
    }

    @Inject
    CompleteOfferViewModel completeOfferViewModel;

    ActivityCompleteOfferBinding binding;

    PostViewModel Post;
    String callForm;
    RatingDialog ratingDialog;
    int role = 0;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_complete_offer;
    }

    @Override
    public CompleteOfferViewModel getViewModel() {
        return completeOfferViewModel;
    }

    @Override
    public void onComplete() {
        completeOfferViewModel.doCompleteOrderApi(this, Post);
    }

    @Override
    public void sendMessage() {
        Intent intent = new Intent(this, CustomHolderMessagesActivity.class);
        intent.putExtra("receiverId", Post.getUserId());
        intent.putExtra("receiverName", Post.getNickname());
        intent.putExtra("receiverAvatar", Post.getUserAvartar());
        intent.putExtra("SenderId", completeOfferViewModel.getDataManager().getCurrentUser().getUid());
        intent.putExtra("Avatar", completeOfferViewModel.getDataManager().getCurrentUser().getImageUrl());
        intent.putExtra("NickName", completeOfferViewModel.getDataManager().getCurrentUser().getImageUrl());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onTracking() {
        Intent intent = new Intent(this, TimeLineActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Post", Post);
        intent.putExtra("Data", bundle);
        if (callForm != null && callForm.equals("transitfragment")) {
            intent.putExtra("Showtracking", false);
        } else if (callForm != null && callForm.equals("STM")) {
            intent.putExtra("Showtracking", true);
        } else if (callForm != null && callForm.equals("receivedfragment")) {
            intent.putExtra("Showtracking", false);
        }
        startActivity(intent);
    }

    @Override
    public void showBtnPayment(int i) {
        if (i == 0) {
            binding.btnPayment.setVisibility(View.GONE);
        } else {
            binding.btnPayment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRating() {
        ratingDialog = RatingDialog.newInstance();
        ratingDialog.setPost(Post.getUserId(), Post.getOrderId(), role);
        ratingDialog.show(this.getSupportFragmentManager());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeOfferViewModel.setNavigator(this);
        binding = getViewDataBinding();
        receiveIntent();

    }

    private void checkRating() {
        completeOfferViewModel.checkRating(Post.getOrderId());
    }

    private void receiveIntent() {
        callForm = getIntent().getStringExtra("callFrom");
        Bundle bundle = getIntent().getBundleExtra("Data");
        Post = (PostViewModel) bundle.getSerializable("Post");
        getSupportActionBar().show();
        completeOfferViewModel.updateData(Post);

        if (callForm != null && callForm.equals("transitfragment")) {
            binding.btnPayment.setVisibility(View.VISIBLE);
            role = 0;
        } else if (callForm != null && callForm.equals("STM")) {
            binding.btnPayment.setVisibility(View.GONE);
            role = 1;
        } else if (callForm != null && callForm.equals("receivedfragment")) {
            binding.btnPayment.setVisibility(View.GONE);
            role = 0;
            checkRating();
        } else if (callForm != null && callForm.equals("STMReceived")) {
            binding.btnPayment.setVisibility(View.GONE);
            role = 1;
            checkRating();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
