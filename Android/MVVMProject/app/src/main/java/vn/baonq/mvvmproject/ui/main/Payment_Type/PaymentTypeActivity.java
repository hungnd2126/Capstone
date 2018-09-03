package vn.baonq.mvvmproject.ui.main.Payment_Type;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.databinding.ActivityTranferMoneyBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;
import vn.baonq.mvvmproject.utils.AppError;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class PaymentTypeActivity extends BaseActivity<ActivityTranferMoneyBinding, PaymentTypeViewModel> implements PaymentTypeNavigator {

    int totalTranfer = 0;
    int total = 0;

    ActivityTranferMoneyBinding activityTranferMoneyBinding;
    @Inject
    PaymentTypeViewModel paymentTypeViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tranfer_money;
    }

    @Override
    public PaymentTypeViewModel getViewModel() {
        return paymentTypeViewModel;
    }

    @Override
    public void onSelected(int selected) {
        switch (selected) {
            case 1:
                totalTranfer = 0;
                activityTranferMoneyBinding.expandableLayout.collapse();
                break;
            case 2:
                totalTranfer = total;
                activityTranferMoneyBinding.expandableLayout.collapse();
                break;
            case 3:
                if (activityTranferMoneyBinding.expandableLayout.isExpanded() == false) {
                    activityTranferMoneyBinding.expandableLayout.toggle();
                    activityTranferMoneyBinding.expandableLayout.setDuration(0);
                }
                break;
        }
    }

    @Override
    public void onPay() {
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        if (activityTranferMoneyBinding.rdtNoTranfer.isChecked()) {
            offerResponse.setDeposit(0);
            paymentTypeViewModel.getCompositeDisposable().add(paymentTypeViewModel.getDataManager()
                    .createOrder(offerResponse)
                    .subscribeOn(paymentTypeViewModel.getSchedulerProvider().io())
                    .observeOn(paymentTypeViewModel.getSchedulerProvider().ui())
                    .subscribe(response -> {
                        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                        intentMain.putExtra("currentMenu", "1");
                        Toasty.success(this, "Xác nhận thành công", Toast.LENGTH_SHORT, true).show();
                        startActivity(intentMain);
                    }, throwable -> {
                        Toasty.error(this, "Thanh toán thất bại", Toast.LENGTH_SHORT, true).show();
                        Log.d("order : Error ", "");
                    }));
        } else if (activityTranferMoneyBinding.rbtTranferAll.isChecked()) {
            offerResponse.setDeposit(totalTranfer);
            paymentTypeViewModel.getCompositeDisposable().add(paymentTypeViewModel.getDataManager()
                    .createOrder(offerResponse)
                    .subscribeOn(paymentTypeViewModel.getSchedulerProvider().io())
                    .observeOn(paymentTypeViewModel.getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response.getStatusCode().equals(AppError.HET_TIEN)) {
                            Toasty.error(this, "Số dư trong tài khoản không đủ", Toast.LENGTH_SHORT, true).show();
                        }
                        else {
                            paymentTypeViewModel.getDataManager().setAmout(response.getCurrent_money());
                            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                            intentMain.putExtra("currentMenu", "1");
                            Toasty.success(this, "Xác nhận thành công", Toast.LENGTH_SHORT, true).show();
                            startActivity(intentMain);
                        }
                    }, throwable -> {
                        Toasty.error(this, "Thanh toán thất bại", Toast.LENGTH_SHORT, true).show();
                        Log.d("order : Error ", "");
                    }));
        } else if (activityTranferMoneyBinding.rbtTranferOrder.isChecked()) {
            totalTranfer = Integer.parseInt(activityTranferMoneyBinding.TotalTranfer.getText().toString());
            offerResponse.setDeposit(totalTranfer);
            paymentTypeViewModel.getCompositeDisposable().add(paymentTypeViewModel.getDataManager()
                    .createOrder(offerResponse)
                    .subscribeOn(paymentTypeViewModel.getSchedulerProvider().io())
                    .observeOn(paymentTypeViewModel.getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response.getStatusCode().equals(AppError.HET_TIEN)) {
                            Toasty.error(this, "Số dư trong tài khoản không đủ", Toast.LENGTH_SHORT, true).show();
                        }
                        else {
                            paymentTypeViewModel.getDataManager().setAmout(response.getCurrent_money());
                            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                            intentMain.putExtra("currentMenu", "1");
                            Toasty.success(this, "Xác nhận thành công", Toast.LENGTH_SHORT, true).show();
                            startActivity(intentMain);
                        }
                    }));
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentTypeViewModel.setNavigator(this);
        activityTranferMoneyBinding = getViewDataBinding();

        getSupportActionBar().show();
        receiveIntent();
        total = offerResponse.getProductPrice() * offerResponse.getQuantity() + offerResponse.getShippingFee() - Post.getDeposit();
        paymentTypeViewModel.Total.set(CommonUtils.formatPrice(total));
    }

    GetListOfferOnPostResponse.OfferResponse offerResponse;
    PostViewModel Post;

    private void receiveIntent() {
        Bundle bundle = getIntent().getBundleExtra("Data");
        offerResponse = (GetListOfferOnPostResponse.OfferResponse) bundle.getSerializable("Offer");
        Post = (PostViewModel) bundle.getSerializable("Post");
    }
}
