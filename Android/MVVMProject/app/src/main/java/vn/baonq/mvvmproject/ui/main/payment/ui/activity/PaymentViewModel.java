package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

import android.databinding.ObservableField;
import android.util.Log;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {
    public static final String TAG = PaymentViewModel.class.getSimpleName();
    public final ObservableField<String> productImage = new ObservableField<>();
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> buyFrom = new ObservableField<>();
    public final ObservableField<String> deliveryTo = new ObservableField<>();
    public final ObservableField<String> shippingFee = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> deliveryDate = new ObservableField<>();
    public final ObservableField<String> travelerName = new ObservableField<>();
    public final ObservableField<String> travlerAvartar = new ObservableField<>();
    public final ObservableField<String> createOffer = new ObservableField<>();
    public final ObservableField<String> deposit = new ObservableField<>();
    public final ObservableField<String> subsist = new ObservableField<>();


    public PaymentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateData(PostViewModel Post){
        buyFrom.set(Post.getBuy_Address().toString());
        deliveryTo.set(Post.getDelivery_Address().toString());
        productImage.set(BASE_URL + Post.getImageUrl());
        productName.set(Post.getProductName());
        shippingFee.set(CommonUtils.formatPrice(Post.getShippingFee()));
        total.set(CommonUtils.formatPrice(Post.getBestPrice()));
        price.set(CommonUtils.formatPrice(Post.getBestPrice() - Post.getShippingFee()));
        deliveryDate.set(Post.getDeliveryDate());
        createOffer.set(Post.getDateCreated());
        travelerName.set(Post.getUsername());
        travlerAvartar.set(BASE_URL + Post.getUserAvartar());
        deposit.set(CommonUtils.formatPrice(Post.getDeposit()));
        subsist.set(CommonUtils.formatPrice(Post.getBestPrice() - Post.getDeposit()));
    }

    public void doCompleOrder(PostViewModel Post){
        getCompositeDisposable().add(getDataManager()
                .doCompletePost(Post)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    deposit.set(CommonUtils.formatPrice(response.getDeposit()));
                    subsist.set(CommonUtils.formatPrice(Post.getBestPrice() - response.getDeposit()));
                    getNavigator().doOnSuccess(response.getOrderId());
                }, throwable -> {
                    getNavigator().doOnError();
                }));
    }

    public void doRutTienApi(double amount) {
        getCompositeDisposable().add(getDataManager()
                .doRutTien(amount)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        getDataManager().setAmout(response.getValue());
                        getNavigator().doOnRutTienTC();
                    }
                }, throwable -> {
                    Log.d(TAG, "Error");
                    getNavigator().doOnRutTienTC();
                }));
    }
}
