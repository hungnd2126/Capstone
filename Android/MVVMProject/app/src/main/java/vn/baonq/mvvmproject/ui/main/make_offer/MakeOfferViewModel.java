package vn.baonq.mvvmproject.ui.main.make_offer;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.util.Log;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.OfferViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class MakeOfferViewModel extends BaseViewModel<MakeOfferNavigator> {
    public static final String TAG = MakeOfferViewModel.class.getSimpleName();
    public ObservableField<String> image, productName, from, to, shippingfee, shippingfeeN, price, message, total, quantity;

    public MakeOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        image = new ObservableField<>("");
        productName = new ObservableField<>("");
        from = new ObservableField<>("");
        to = new ObservableField<>("");
        shippingfee = new ObservableField<>("");
        price = new ObservableField<>("");
        message = new ObservableField<>("");
        shippingfeeN = new ObservableField<>("");
        total = new ObservableField<>("");
        quantity = new ObservableField<>("");
    }

    public void setData(PostViewModel postViewModel, GetTripByUserResponse.TripItemResponse trip) {
        image.set(BASE_URL + postViewModel.getImageUrl());
        productName.set(postViewModel.getProductName());
        if(postViewModel.getDeliveryTo()==null||postViewModel.getDeliveryTo().equals("")){
            from.set(postViewModel.getDelivery_Address().toString());
            to.set(postViewModel.getBuy_Address().toString());
        }else{
        from.set(postViewModel.getDeliveryTo());
        to.set(postViewModel.getBuyFrom());}
        shippingfee.set(String.valueOf((int) postViewModel.getShippingFee()));
        price.set(CommonUtils.formatPrice(postViewModel.getPrice()));
        quantity.set(String.valueOf(postViewModel.getQuantity()));
        if(trip!=null){
        message.set("Xin chào, "+trip.getTravelDate()+" tôi sẽ đi "+trip.getName()+" tôi có thể giúp bạn mua");}
        else  if(postViewModel.getMessage()!=null){
            message.set(postViewModel.getMessage());
        }
        total.set(CommonUtils.formatPrice(postViewModel.getPrice() + postViewModel.getShippingFee()));
    }

    public void makeOfferApiCall(OfferViewModel offerViewModel) {
        getCompositeDisposable().add(getDataManager()
                .makeOffer(offerViewModel)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getId() != 0) {
                        getNavigator().onCompleteRequest(true);
                    }
                    ProcessBar.endProgress();
                    Log.d(TAG, "success");
                }, throwable -> {
                    ProcessBar.endProgress();
                    Log.d(TAG, "error");
                }));
    }

    public void updateOfferApiCall(OfferViewModel offerViewModel) {
        getCompositeDisposable().add(getDataManager()
                .updateOffer(offerViewModel)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getId() != 0) {
                        getNavigator().onCompleteRequest(true);
                    }
                    Log.d(TAG, "success");
                    ProcessBar.endProgress();

                }, throwable -> {
                    ProcessBar.endProgress();
                    Log.d(TAG, "error");
                }));
    }

    public void makeOffer() {
        getNavigator().onClickMakeOffer();
    }

    public void getTime() {
        getNavigator().onClickDateTimePicker();
    }

}
