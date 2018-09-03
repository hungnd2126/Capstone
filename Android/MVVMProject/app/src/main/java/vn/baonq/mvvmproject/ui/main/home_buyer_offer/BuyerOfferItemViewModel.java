package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class BuyerOfferItemViewModel {
    public final ObservableField<String> ImageUrl,
            dateCreated, deliveryTo, buyFrom, productName, OfferSize, deliveryDate;

    public final BuyerOfferItemViewModel.BuyerOfferItemListener buyerOfferItemListener;

    public BuyerOfferItemViewModel(PostViewModel model, BuyerOfferItemListener buyerOfferItemListener) {
        productName = new ObservableField<>(model.getProductName());
        ImageUrl = new ObservableField<>(BASE_URL + model.getImageUrl());
        deliveryTo = new ObservableField<>(model.getDelivery_Address().toString());
        buyFrom = new ObservableField<>(model.getBuy_Address().toString());
        OfferSize = new ObservableField<>(model.getOfferSize());
        deliveryDate = new ObservableField<>(model.getDeliveryDate());
        dateCreated = new ObservableField<>(model.getDateCreated());
        this.buyerOfferItemListener = buyerOfferItemListener;
    }

    public void onItemClick() {
        buyerOfferItemListener.onItemClick();
    }

    public interface BuyerOfferItemListener {
        void onItemClick();
    }
}