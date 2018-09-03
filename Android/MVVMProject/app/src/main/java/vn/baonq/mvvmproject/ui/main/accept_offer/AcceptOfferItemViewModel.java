package vn.baonq.mvvmproject.ui.main.accept_offer;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse.OfferResponse;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class AcceptOfferItemViewModel {
    public final ObservableField<Integer> Id;
    public final ObservableField<Float> rate;
    public final ObservableField<String> traveler_name, date_request, avatar_Url;
    public final ObservableField<String> productPrice, shippingFee, total, travelTo, deliveryDate, quantity,message;

    public final AcceptOfferItemListener acceptOfferItemListener;

    public AcceptOfferItemViewModel(OfferResponse model, AcceptOfferItemListener requestedItemListener) {
        Id = new ObservableField<>(model.getOfferId());
        rate = new ObservableField<>(model.getRating());
        traveler_name = new ObservableField<>(model.getTravelerName());
        date_request = new ObservableField<>(model.getCreatedDate());
        avatar_Url = new ObservableField<>(BASE_URL + model.getTravelerImage());
        travelTo = new ObservableField<>(model.getTravelTo());
        deliveryDate = new ObservableField<>(model.getDeliveryDate());
        productPrice = new ObservableField<>(CommonUtils.formatPrice(model.getProductPrice()));
        shippingFee = new ObservableField<>(CommonUtils.formatPrice(model.getShippingFee()));
        quantity = new ObservableField<>(String.valueOf(model.getQuantity()));
        total = new ObservableField<>(CommonUtils.formatPrice(model.getShippingFee() + model.getProductPrice() * model.getQuantity()));
        message= new ObservableField<>(model.getMessage());
        this.acceptOfferItemListener = requestedItemListener;
    }

    public void onSendMessage() {
        acceptOfferItemListener.onSendMessage();
    }

    public void onAcceptOffer(int offerId, String total) {
        acceptOfferItemListener.onAcceptOffer(offerId, total);
    }

    public interface AcceptOfferItemListener {
        void onSendMessage();

        void onAcceptOffer(int offerId, String total);

    }
}
