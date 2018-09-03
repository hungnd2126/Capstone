package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.view.View;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class STMReceivedItemViewModel {
    public final ObservableField<Integer> postId;
    public final ObservableField<String> productName;
    public final ObservableField<String> total;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> imageUrl;
    public final ObservableField<String> travelerId;
    public final ObservableField<String> travelerAvartar;
    public final ObservableField<String> travelerName;
    public final ObservableField<String> deliveryDate;

    public final STMReceivedItemListener stmReceivedItemListener;

    public STMReceivedItemViewModel(PostViewModel post, STMReceivedItemListener stmReceivedItemListener){
        postId = new ObservableField<>(post.getId());
        productName = new ObservableField<>(post.getProductName());
        createdDate = new ObservableField<>(post.getDateCreated());
        total = new ObservableField<>(CommonUtils.formatPrice(post.getBestPrice()));
        imageUrl = new ObservableField<>(BASE_URL + post.getImageUrl());
        travelerId = new ObservableField<>(post.getUserId());
        travelerAvartar = new ObservableField<>(BASE_URL + post.getUserAvartar());
        travelerName = new ObservableField<>(post.getNickname());
        deliveryDate = new ObservableField<>(post.getDeliveryDate());

        this.stmReceivedItemListener = stmReceivedItemListener;
    }

    public void onItemClick(){
        stmReceivedItemListener.onItemClick();
    }
    public void sendMessage() {
        stmReceivedItemListener.sendMessage();
    }

    public interface STMReceivedItemListener{
        void onItemClick();
        void sendMessage();
    }
}
