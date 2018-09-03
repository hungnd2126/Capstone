package vn.baonq.mvvmproject.ui.main.more.credit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityCreditBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.utils.AppAction.KEY_ACTION;
import static vn.baonq.mvvmproject.utils.AppAction.NAP_TIEN;
import static vn.baonq.mvvmproject.utils.AppAction.RUT_TIEN;

public class CreditActivity extends BaseActivity<ActivityCreditBinding, CreditViewModel> implements CreditNavigator {
    public static final String TAG = CreditActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreditActivity.class);
        return intent;
    }

    @Inject
    CreditViewModel creditViewModel;
    private ActivityCreditBinding binding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    CreditItemAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit;
    }

    @Override
    public CreditViewModel getViewModel() {
        return creditViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        creditViewModel.setNavigator(this);
        binding = getViewDataBinding();
    }

    private void initUI(){
        ProcessBar.runProgress(this);
        setUp();
        setLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        initUI();
    }

    @Override
    public void saveCredit() {
        Log.d(TAG, "saveCredit");
    }

    @Override
    public void updateCompleted(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    public void napTien() {
        Intent intent = PaymentActivity.newIntent(getApplicationContext());
        intent.putExtra(KEY_ACTION, NAP_TIEN);
        intent.putExtra("TKNL", creditViewModel.getDataManager().getCurrentUser().getTknl());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void rutTien() {
        Intent intent = PaymentActivity.newIntent(getApplicationContext());
        intent.putExtra(KEY_ACTION, RUT_TIEN);
        intent.putExtra("TKNL", creditViewModel.getDataManager().getCurrentUser().getTknl());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void getDataComplete() {
        creditViewModel.getAmount();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.requestedRecyclerView.setLayoutManager(mLayoutManager);
        binding.requestedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.requestedRecyclerView.setAdapter(adapter);
    }

    public void setLiveData() {
        creditViewModel.getData();
        creditViewModel.getPostLiveData().observe(this, transit -> creditViewModel.addHistoryItem(transit));
    }
}
