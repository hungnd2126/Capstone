package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.ActivityCheckorderBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.payment.api.CheckOrderRequest;
import vn.baonq.mvvmproject.ui.main.payment.bean.CheckOrderBean;
import vn.baonq.mvvmproject.ui.rating.RatingDialog;
import vn.baonq.mvvmproject.utils.Commons;
import vn.baonq.mvvmproject.utils.Constant;

import static vn.baonq.mvvmproject.utils.AppAction.NAP_TIEN;

public class CheckOrderActivity extends BaseActivity<ActivityCheckorderBinding, PaymentViewModel> implements
        CheckOrderRequest.CheckOrderRequestOnResult, PaymentNavigator,
        HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    public static final String TOKEN_CODE = "token_code";

    private ProgressView mProgressView;
    ActivityCheckorderBinding binding;
    RatingDialog dialog;
    private String mTokenCode = "";
    @Inject
    PaymentViewModel paymentViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_checkorder;
    }

    @Override
    public PaymentViewModel getViewModel() {
        return paymentViewModel;
    }

    PostViewModel Post;
    private String ACTION1, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        getSupportActionBar().show();
        dialog = RatingDialog.newInstance();
        paymentViewModel.setNavigator(this);
        ACTION1 = getIntent().getStringExtra("NEW_ACTION");
        if (ACTION1 != null && ACTION1.equals(NAP_TIEN)) {
            mTokenCode = getIntent().getStringExtra(TOKEN_CODE);
            amount = getIntent().getStringExtra("amount");
        }

        initView();
    }

    private void initView() {
        mProgressView = findViewById(R.id.activity_checkorder_progressView);
        checkOrderObject();
    }

    private void checkOrderObject() {
        CheckOrderBean checkOrderBean = new CheckOrderBean();
        checkOrderBean.setFunc("checkOrder");
        checkOrderBean.setVersion("1.0");
        checkOrderBean.setMerchantID(Constant.MERCHANT_ID);
        checkOrderBean.setTokenCode(mTokenCode);
        String checksum = getChecksum(checkOrderBean);
        checkOrderBean.setChecksum(checksum);
        CheckOrderRequest checkOrderRequest = new CheckOrderRequest();
        checkOrderRequest.execute(getApplicationContext(), checkOrderBean);
        checkOrderRequest.getCheckOrderRequestOnResult(this);
        mProgressView.setVisibility(View.VISIBLE);
    }

    private String getChecksum(CheckOrderBean checkOrderBean) {
        String stringSendOrder = checkOrderBean.getFunc() + "|" +
                checkOrderBean.getVersion() + "|" +
                checkOrderBean.getMerchantID() + "|" +
                checkOrderBean.getTokenCode() + "|" +
                Constant.MERCHANT_PASSWORD;
        String checksum = Commons.md5(stringSendOrder);
        return checksum;
    }

    @Override
    public void onBackPressed() {
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        intentMain.putExtra("currentMenu", "1");
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentMain);
        finish();
    }

    @Override
    public void onCheckOrderRequestOnResult(boolean result, String data) {
        if (result == true) {
            try {
                JSONObject objResult = new JSONObject(data);
                String responseCode = objResult.getString("response_code");
                if (responseCode.equalsIgnoreCase("00")) {
                    if (ACTION1 != null && ACTION1.equals(NAP_TIEN)) {
                        paymentViewModel.getCompositeDisposable().add(paymentViewModel.getDataManager()
                                .doNapTien(Double.parseDouble(amount))
                                .subscribeOn(paymentViewModel.getSchedulerProvider().io())
                                .observeOn(paymentViewModel.getSchedulerProvider().ui())
                                .subscribe(response -> {
                                            paymentViewModel.getDataManager().setAmout(response.getValue());
                                            mProgressView.setVisibility(View.GONE);
                                            Toast.makeText(this, "Nạp tiền thành công", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }, throwable -> {
                                            mProgressView.setVisibility(View.GONE);
                                            Log.d("Create Post Error: ", throwable.toString());
                                        }
                                ));
                    }
                } else {
                    mProgressView.setVisibility(View.GONE);
                    showErrorDialog(Commons.getCodeError(getApplicationContext(), responseCode), false);
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

    private void showErrorDialog(String message, final boolean isExit) {
        final Dialog mSuccessDialog = new Dialog(CheckOrderActivity.this);
        mSuccessDialog.setContentView(R.layout.dialog_success);
        mSuccessDialog.setCancelable(false);
        mSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mSuccessDialog.getWindow().setGravity(Gravity.CENTER);
        TextView txtContent = mSuccessDialog.findViewById(R.id.dialog_success_txtContent);
        txtContent.setText(message);
        Button btnClose = mSuccessDialog.findViewById(R.id.dialog_success_btnClose);
        btnClose.setOnClickListener(v -> {
            mSuccessDialog.dismiss();
            if (isExit) {
                finish();
            }
        });

        mSuccessDialog.show();
    }

    @Override
    public void doOnSuccess(int orderId) {
        Toasty.success(this, "Thanh toán thành công", Toast.LENGTH_SHORT, true).show();
        mProgressView.setVisibility(View.GONE);
        dialog.show(this.getSupportFragmentManager());
        dialog.setPost(Post.getUserId(), orderId, 0);

    }

    @Override
    public void doOnError() {
        Toasty.error(this, "Thanh toán thất bại", Toast.LENGTH_SHORT, true).show();
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void doOnRutTienTC() {

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
