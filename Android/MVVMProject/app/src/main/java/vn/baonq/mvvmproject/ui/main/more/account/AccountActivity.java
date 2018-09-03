package vn.baonq.mvvmproject.ui.main.more.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityAccountBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class AccountActivity extends BaseActivity<ActivityAccountBinding, AccountViewModel> implements AccountNavigator{

    public static final String TAG = AccountActivity.class.getSimpleName();

    @Inject
    AccountViewModel accountViewModel;

    private ActivityAccountBinding activityAccountBinding;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return  R.layout.activity_account;
    }

    @Override
    public AccountViewModel getViewModel() {
        return accountViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        accountViewModel.setNavigator(this);
        activityAccountBinding = getViewDataBinding();
        initUI();
    }

    private void initUI(){
        accountViewModel.setAccount();
        activityAccountBinding.accountEmail.setText(accountViewModel.getEmail());
    }

    @Override
    public void saveAccount() {
        Log.d(TAG, "saveAccount");
        ProcessBar.runProgress(this);
        String email = activityAccountBinding.accountEmail.getText().toString();
        String newpass = activityAccountBinding.accountNewPass.getText().toString();
        String confirmpass = activityAccountBinding.accountConfirmPass.getText().toString();
        accountViewModel.UpdateInfo(email, newpass, confirmpass);
    }

    @Override
    public void updateCompleted(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

}
