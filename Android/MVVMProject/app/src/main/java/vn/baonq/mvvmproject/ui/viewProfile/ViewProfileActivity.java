package vn.baonq.mvvmproject.ui.viewProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityAddpostBinding;
import vn.baonq.mvvmproject.databinding.ActivityViewProfileBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class ViewProfileActivity extends BaseActivity<ActivityViewProfileBinding, ViewProfileViewModel> implements ViewProfileNavigator {
    public static final String TAG = ViewProfileActivity.class.getSimpleName();
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ViewProfileActivity.class);
        return intent;
    }

    @Inject
    ViewProfileViewModel viewModel;

    @Inject
    ReviewItemAdapter adapter;
    @Inject
    LinearLayoutManager manager;

    private ActivityViewProfileBinding binding;

    private String userId;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_profile;
    }

    @Override
    public ViewProfileViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        ProcessBar.runProgress(this);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        userId = getIntent().getStringExtra("userId");
        if (userId != null){
            viewModel.callData(userId);
        }
        setUp();
        setLiveData();
        ProcessBar.endProgress();

    }


    private void setUp() {
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.viewProfileRecyclerView.setLayoutManager(manager);
        binding.viewProfileRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.viewProfileRecyclerView.setAdapter(adapter);
    }

    public void setLiveData() {
        viewModel.getData(userId);
        viewModel.getReviewLiveData().observe(this, transit -> viewModel.addReviewItem(transit));
    }
}
