package vn.baonq.mvvmproject.ui.main.detail_order;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferItemViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class DetailOfferItemViewModel{
        public final ObservableField<Integer> Id;
        public final ObservableField<Float> rate;
        public final ObservableField<String> traveler_name, date_request, avatar_Url;
        public final ObservableField<String> productPrice, shippingFee, total, travelTo, deliveryDate;
        public DetailOfferItemViewModel(GetListOfferOnPostResponse.OfferResponse model) {
            Id = new ObservableField<>(model.getOfferId());
            rate = new ObservableField<>(model.getRating());
            traveler_name = new ObservableField<>(model.getTravelerName());
            date_request = new ObservableField<>(model.getCreatedDate());
            avatar_Url = new ObservableField<>(BASE_URL + model.getTravelerImage());
            travelTo = new ObservableField<>(model.getTravelTo());
            deliveryDate = new ObservableField<>(model.getDeliveryDate());
            productPrice = new ObservableField<>(CommonUtils.formatPrice(model.getProductPrice()));
            shippingFee = new ObservableField<>(CommonUtils.formatPrice(model.getShippingFee()));
            total = new ObservableField<>(CommonUtils.formatPrice(model.getShippingFee() + model.getProductPrice() * model.getQuantity()));
        }

}
