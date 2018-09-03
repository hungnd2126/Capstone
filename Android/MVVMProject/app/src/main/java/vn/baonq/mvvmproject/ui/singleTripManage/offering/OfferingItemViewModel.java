package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class OfferingItemViewModel {

    public final ObservableField<String> shippingFee, quantity, price, description, ImageUrl, dateUpdated,
            dateCreated, deliveryTo, buyFrom, productName, OfferSize,
            Username, UserAvartar, deposit, deliveryDate;
    public final ObservableField<Integer> id;
    public final OfferingItemListener offeringItemListener;

    public OfferingItemViewModel(PostViewModel post, OfferingItemListener offeringItemListener) {
        id = new ObservableField<>(post.getId());
        shippingFee = new ObservableField<>(CommonUtils.formatPrice(post.getShippingFee()));
        quantity = new ObservableField<>(String.valueOf(post.getQuantity()));
        price = new ObservableField<>(CommonUtils.formatPrice(post.getPrice()));
        description = new ObservableField<>(post.getDescription());
        ImageUrl = new ObservableField<>(ApiEndPoint.BASE_URL + "/" + post.getImageUrl());
        dateUpdated = new ObservableField<>(post.getDateUpdated());
        dateCreated = new ObservableField<>(post.getDateCreated());
        deliveryTo = new ObservableField<>(post.getDeliveryTo());
        buyFrom = new ObservableField<>();
        if(post.getDomain()!=null&&!post.getDomain().equals("")){
            buyFrom.set(post.getDomain()+" - "+post.getBuyFrom());
        }else{
            buyFrom.set(post.getBuyFrom());
        }
        productName = new ObservableField<>(post.getProductName());
        OfferSize = new ObservableField<>(post.getOfferSize());
        Username = new ObservableField<>(post.getNickname());
        deposit = new ObservableField<>(CommonUtils.formatPrice(post.getDeposit()));
        UserAvartar = new ObservableField<>(ApiEndPoint.BASE_URL + post.getUserAvartar());
        deliveryDate = new ObservableField<>(post.getDeliveryDate());
        this.offeringItemListener = offeringItemListener;
    }

    public void viewDetail() {
        offeringItemListener.viewDetail();
    }

    public void makeOffer() {
        offeringItemListener.makeOffer();
    }

    public interface OfferingItemListener {
        void viewDetail();

        void makeOffer();
    }
}
