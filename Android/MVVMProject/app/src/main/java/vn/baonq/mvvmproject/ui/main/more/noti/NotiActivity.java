package vn.baonq.mvvmproject.ui.main.more.noti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityNotificationsBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class NotiActivity extends BaseActivity<ActivityNotificationsBinding, NotiViewModel> implements NotiNavigator {
    @Inject
    NotiViewModel viewModel;
    private ActivityNotificationsBinding binding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    NotiItemAdapter adapter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NotiActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notifications;
    }

    @Override
    public NotiViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        ProcessBar.runProgress(this);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
        setUp();
        setLiveData();
        ProcessBar.endProgress();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.fragmentNotiRecyclerView.setLayoutManager(mLayoutManager);
        binding.fragmentNotiRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.fragmentNotiRecyclerView.setAdapter(adapter);
    }

    public void setLiveData() {
        viewModel.getData();
        viewModel.getNotiLiveData().observe(this, transit -> viewModel.addNotiItem(transit));
    }

}
