package vn.baonq.mvvmproject.ui.complete_offer;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.AppError;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class CompleteOfferViewModel extends BaseViewModel<CompleteOfferNavigator> {

    //<editor-fold desc="properties">
    public final ObservableField<String> productImage = new ObservableField<>();
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> buyFrom = new ObservableField<>();
    public final ObservableField<String> deliveryTo = new ObservableField<>();
    public final ObservableField<String> shippingFee = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> deliveryDate = new ObservableField<>();
    public final ObservableField<String> travelerName = new ObservableField<>();
    public final ObservableField<String> travlerAvartar = new ObservableField<>();
    public final ObservableField<String> createOffer = new ObservableField<>();
    public final ObservableField<String> deposit = new ObservableField<>();
    public final ObservableField<String> subsist = new ObservableField<>();
    //</editor-fold>

    public CompleteOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void sendMessage() {
        getNavigator().sendMessage();
    }

    public void onComplete() {
        getNavigator().onComplete();
    }

    public void onTracking() {
        getNavigator().onTracking();
    }

    public void doCompleteOrderApi(Context context, PostViewModel Post) {
        getCompositeDisposable().add(getDataManager()
                .doCompletePost(Post)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response.getStatusCode().equals(AppError.HET_TIEN)) {
                        Toasty.success(context, "Tài khoản của bạn không đủ", Toast.LENGTH_SHORT, true).show();
                    } else {
                        deposit.set(CommonUtils.formatPrice(response.getDeposit()));
                        subsist.set(CommonUtils.formatPrice(Post.getBestPrice() - response.getDeposit()));
                        getDataManager().setAmout(response.getCurrent_money());
                        Toasty.success(context, "Thanh toán thành công", Toast.LENGTH_SHORT, true).show();
                        getNavigator().showBtnPayment(0);
                        getNavigator().showRating();
                    }
                }, throwable -> {
                    Toasty.error(context, "Thanh toán thất bại", Toast.LENGTH_SHORT, true).show();
                }));
    }

    public void updateData(PostViewModel Post) {
        try {
            buyFrom.set(Post.getBuy_Address().toString());
            deliveryTo.set(Post.getDelivery_Address().toString());
            productImage.set(BASE_URL + "/" + Post.getImageUrl());
            productName.set(Post.getProductName());
            shippingFee.set(CommonUtils.formatPrice(Post.getShippingFee()));
            total.set(CommonUtils.formatPrice(Post.getBestPrice()));
            price.set(CommonUtils.formatPrice(Post.getBestPrice() - Post.getShippingFee()));
            deliveryDate.set(Post.getDeliveryDate());
            createOffer.set(Post.getDateCreated());
            travelerName.set(Post.getNickname());
            quantity.set(Post.getQuantity() + "");
            travlerAvartar.set(BASE_URL + "/" + Post.getUserAvartar());
            deposit.set(CommonUtils.formatPrice(Post.getDeposit()));
            subsist.set(CommonUtils.formatPrice(Post.getBestPrice() - Post.getDeposit()));
        } catch (Exception e) {

        }
    }

    public void checkRating(int orderId) {
        getCompositeDisposable().add(getDataManager()
                .checkRating(orderId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.equals("false")) {
                        getNavigator().showRating();
                    }
                }, throwable -> {
                }));
    }

}
