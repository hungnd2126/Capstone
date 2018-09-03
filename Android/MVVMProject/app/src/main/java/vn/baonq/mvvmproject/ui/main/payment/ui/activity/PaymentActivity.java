package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import javax.inject.Inject;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.databinding.ActivityPaymentBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.payment.api.SendOrderRequest;
import vn.baonq.mvvmproject.ui.main.payment.bean.SendOrderBean;
import vn.baonq.mvvmproject.utils.Commons;
import vn.baonq.mvvmproject.utils.Constant;
import vn.baonq.mvvmproject.utils.ProcessBar;

import static vn.baonq.mvvmproject.utils.AppAction.KEY_ACTION;
import static vn.baonq.mvvmproject.utils.AppAction.NAP_TIEN;
import static vn.baonq.mvvmproject.utils.AppAction.RUT_TIEN;


public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentViewModel> implements View.OnClickListener, SendOrderRequest.SendOrderRequestOnResult, PaymentNavigator {
    public static final String TAG = PaymentActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        return intent;
    }

    @Inject
    PaymentViewModel viewModel;

    private EditText editAmount;
    private EditText editEmail;
    private Button btnSendOrder;
    private ProgressView progressView;
    private String ACTION;
    String amount, tknl;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public PaymentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        viewModel.setNavigator(this);
        receiveIntent();
        initView();
    }

    private void initView() {
        editAmount = findViewById(R.id.activity_main_editAmount);
        editEmail = findViewById(R.id.activity_main_editEmail);
        editEmail.setText(tknl);
        btnSendOrder = findViewById(R.id.activity_main_btnSendOrder);
        progressView = findViewById(R.id.activity_main_progressView);
        if (!Commons.checkInternetConnection(getApplicationContext())) {
            showErrorDialog(getString(R.string.error_disconnect), true);
        }
        btnSendOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_main_btnSendOrder:
                ProcessBar.runProgress(this);
                String fullName = "Nguyễn Quốc Bảo";
                String email = "hello@gmail.com";
                String phoneNumber = "0946038348";
                amount = editAmount.getText().toString();
                String address = "123 HCM, VN";
                if (!amount.equalsIgnoreCase("")) {
                    if (ACTION != null && ACTION.equals(NAP_TIEN)){
                        sendOrderObject(fullName, Integer.parseInt(amount)
                                , email, phoneNumber, address);
                    }else if (ACTION != null && ACTION.equals(RUT_TIEN)){
                        viewModel.doRutTienApi(Double.valueOf(amount));
                    }

                } else {
                    showErrorDialog("Bạn cần nhập số tiền cần thanh toán", false);
                }

                break;
        }
    }

    //<editor-fold desc=" function ngân lượng khỏi check">
    private void sendOrderObject(String fullName, int amount, String email, String phoneNumber, String address) {
        SendOrderBean sendOrderBean = new SendOrderBean();
        sendOrderBean.setFunc("sendOrder");
        sendOrderBean.setVersion("1.0");
        sendOrderBean.setMerchantID(Constant.MERCHANT_ID);
        sendOrderBean.setMerchantAccount("hungnd2126@gmail.com");
        sendOrderBean.setOrderCode("123456DEMO");
        sendOrderBean.setTotalAmount((amount));
        sendOrderBean.setCurrency("vnd");
        sendOrderBean.setLanguage("vi");
        sendOrderBean.setReturnUrl(Constant.RETURN_URL);
        sendOrderBean.setCancelUrl(Constant.CANCEL_URL);
        sendOrderBean.setNotifyUrl(Constant.NOTIFY_URL);
        sendOrderBean.setBuyerFullName(fullName);
        sendOrderBean.setBuyerEmail(email);
        sendOrderBean.setBuyerMobile(phoneNumber);
        sendOrderBean.setBuyerAddress(address);

        String checksum = getChecksum(sendOrderBean);
        sendOrderBean.setChecksum(checksum);

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.execute(getApplicationContext(), sendOrderBean);
        sendOrderRequest.getSendOrderRequestOnResult(this);
        progressView.setVisibility(View.VISIBLE);
    }

    private String getChecksum(SendOrderBean sendOrderBean) {
        String stringSendOrder = sendOrderBean.getFunc() + "|" +
                sendOrderBean.getVersion() + "|" +
                sendOrderBean.getMerchantID() + "|" +
                sendOrderBean.getMerchantAccount() + "|" +
                sendOrderBean.getOrderCode() + "|" +
                sendOrderBean.getTotalAmount() + "|" +
                sendOrderBean.getCurrency() + "|" +
                sendOrderBean.getLanguage() + "|" +
                sendOrderBean.getReturnUrl() + "|" +
                sendOrderBean.getCancelUrl() + "|" +
                sendOrderBean.getNotifyUrl() + "|" +
                sendOrderBean.getBuyerFullName() + "|" +
                sendOrderBean.getBuyerEmail() + "|" +
                sendOrderBean.getBuyerMobile() + "|" +
                sendOrderBean.getBuyerAddress() + "|" +
                Constant.MERCHANT_PASSWORD;
        String checksum = Commons.md5(stringSendOrder);

        return checksum;
    }

    private void showErrorDialog(String message, final boolean isExit) {
        final Dialog mSuccessDialog = new Dialog(PaymentActivity.this);
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
    //</editor-fold>

    @Override
    public void onSendOrderRequestOnResult(boolean result, String data) {
        if (result == true) {
            try {
                JSONObject objResult = new JSONObject(data);
                String responseCode = objResult.getString("response_code");
                if (responseCode.equalsIgnoreCase("00")) {
                    String tokenCode = objResult.getString("token_code");
                    String checkoutUrl = objResult.getString("checkout_url");
                    Intent intentCheckout = new Intent(getApplicationContext(), CheckOutActivity.class);
                    intentCheckout.putExtra(CheckOutActivity.TOKEN_CODE, tokenCode);
                    intentCheckout.putExtra(CheckOutActivity.CHECKOUT_URL, checkoutUrl);
                    intentCheckout.putExtra("NEW_ACTION", ACTION);
                    intentCheckout.putExtra("amount", amount);
                    intentCheckout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCheckout);
                    finish();
                } else {
                    progressView.setVisibility(View.GONE);
                    showErrorDialog(Commons.getCodeError(getApplicationContext(), responseCode), false);
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }

    private void receiveIntent() {
        ACTION = getIntent().getStringExtra(KEY_ACTION);
        tknl = getIntent().getStringExtra("TKNL");
    }

    @Override
    public void doOnSuccess(int orderId) {

    }

    @Override
    public void doOnError() {

    }

    @Override
    public void doOnRutTienTC() {
        ProcessBar.endProgress();
        finish();
    }
}
