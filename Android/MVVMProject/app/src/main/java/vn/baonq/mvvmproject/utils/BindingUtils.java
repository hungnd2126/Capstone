package vn.baonq.mvvmproject.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Notification;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.ReviewViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferAdapter;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryAdapter;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferAdapter;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferModel;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditItemAdapter;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiItemAdapter;
import vn.baonq.mvvmproject.ui.main.order.Received.ReceivedAdapter;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedAdapter;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitAdapter;
import vn.baonq.mvvmproject.ui.singleTripManage.STMReceived.STMReceivedAdapter;
import vn.baonq.mvvmproject.ui.singleTripManage.STMTransit.STMTransitAdapter;
import vn.baonq.mvvmproject.ui.viewProfile.ReviewItemAdapter;

public final class BindingUtils {
    private BindingUtils() {
    }


    @BindingAdapter({"accept_adapter"})
    public static void adAcceptOfferItems(RecyclerView recyclerView, List<GetListOfferOnPostResponse.OfferResponse> items) {
        AcceptOfferAdapter adapter = (AcceptOfferAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"transit_adapter"})
    public static void addTransitItems(RecyclerView recyclerView, List<PostViewModel> items) {
        TransitAdapter adapter = (TransitAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"history_adapter"})
    public static void addHistoryItems(RecyclerView recyclerView, List<HistoryViewModel> items) {
        CreditItemAdapter adapter = (CreditItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView, List<PostViewModel> items) {
        RequestedAdapter adapter = (RequestedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"received_adapter"})
    public static void addReceivedItems(RecyclerView recyclerView, List<PostViewModel> items) {
        ReceivedAdapter adapter = (ReceivedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"review_adapter"})
    public static void addReviewItems(RecyclerView recyclerView, List<ReviewViewModel> items) {
        ReviewItemAdapter adapter = (ReviewItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"noti_adapter"})
    public static void addNotiItem(RecyclerView recyclerView, List<Notification> items) {
        NotiItemAdapter adapter = (NotiItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"delivery_adapter"})
    public static void addDeliveryItems(RecyclerView recyclerView, List<GetTripByUserResponse.TripItemResponse> items) {
        DeliveryAdapter adapter = (DeliveryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            Context context = imageView.getContext();
            Glide.with(context).load(url).into(imageView);
        }
    }

    @BindingAdapter({"stm_received_adapter"})
    public static void addSTMReceivedItems(RecyclerView recyclerView, List<PostViewModel> items) {
        STMReceivedAdapter adapter = (STMReceivedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    @BindingAdapter({"stm_transit_adapter"})
    public static void addSTMTransitItems(RecyclerView recyclerView, List<PostViewModel> items) {
        STMTransitAdapter adapter = (STMTransitAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }
}
