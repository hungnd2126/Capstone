package vn.baonq.mvvmproject.ui.main.more.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityPhoneBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;

public class PhoneActivity extends BaseActivity<ActivityPhoneBinding, PhoneViewModel> implements PhoneNavigator{

    public static final String TAG = PhoneActivity.class.getSimpleName();

    @Inject
    PhoneViewModel phoneViewModel;

    private ActivityPhoneBinding activityPhoneBinding;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PhoneActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return  R.layout.activity_phone;
    }

    @Override
    public PhoneViewModel getViewModel() {
        return phoneViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        phoneViewModel.setNavigator(this);
        activityPhoneBinding = getViewDataBinding();
    }

    @Override
    public void savePhone() {
        Log.d(TAG, "savePhone");
        String phone = activityPhoneBinding.phoneNumber.getText().toString();
        phoneViewModel.UpdateInfo(phone);
    }

    @Override
    public void updateCompleted(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }
}
