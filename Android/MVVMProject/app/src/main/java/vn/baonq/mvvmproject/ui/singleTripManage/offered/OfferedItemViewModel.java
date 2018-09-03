package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class OfferedItemViewModel {

    public final ObservableField<String> id, shippingFee, price, ImageUrl,
            deliveryDate, productName, avartarUrl, BuyerName;
    public final OfferedItemListener offeredItemListener;

    public OfferedItemViewModel(PostViewModel post, OfferedItemListener offeredItemListener) {
        id = new ObservableField<>(post.getId()+"");
        shippingFee = new ObservableField<>(CommonUtils.formatPrice(post.getShippingFee()));
        price = new ObservableField<>(CommonUtils.formatPrice(post.getPrice()));
        ImageUrl = new ObservableField<>(BASE_URL + post.getImageUrl());
        productName = new ObservableField<>(post.getProductName());
        avartarUrl = new ObservableField<>(BASE_URL + post.getUserAvartar());
        BuyerName = new ObservableField<>(post.getUsername());
        deliveryDate = new ObservableField<>(post.getDeliveryDate());
        this.offeredItemListener = offeredItemListener;
    }

    public void viewDetail() {
        offeredItemListener.viewDetail();
    }
    public void deleteOffer(){
        offeredItemListener.deleteOffer();
    }

    public void updateOffer(){
        offeredItemListener.updateOffer();
    }

    public interface OfferedItemListener {
        void viewDetail();
        void deleteOffer();
        void updateOffer();
    }
}
