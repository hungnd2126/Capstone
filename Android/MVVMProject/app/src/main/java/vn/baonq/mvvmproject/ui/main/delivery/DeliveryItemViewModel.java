package vn.baonq.mvvmproject.ui.main.delivery;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.data.model.db.Item;
import vn.baonq.mvvmproject.utils.CommonUtils;

public class DeliveryItemViewModel {
    public final ObservableField<String> TripId;
    public final ObservableField<String> CreatedDate;
    public final ObservableField<String> Name;
    public final ObservableField<String> TravelDate;
    public final ObservableField<String> NumberOrder;
    public final ObservableField<String> Earning;


    public final DeliveryItemListener requestedItemListener;

    public DeliveryItemViewModel(GetTripByUserResponse.TripItemResponse post, DeliveryItemListener listener) {
        TripId = new ObservableField<>("" +post.getTripId());
        CreatedDate = new ObservableField<>(post.getCreatedDate());
        Name = new ObservableField<>(post.getName());
        TravelDate = new ObservableField<>(post.getTravelDate());
        NumberOrder = new ObservableField<>("" + post.getNumberOrder());
        Earning = new ObservableField<>(CommonUtils.formatPrice(post.getEarning()));
        this.requestedItemListener = listener;
    }

    public void onItemClick() { requestedItemListener.onItemClick();}

    public interface DeliveryItemListener{
        void onItemClick();
    }
}
