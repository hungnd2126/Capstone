package vn.baonq.mvvmproject.ui.main.order.Requested;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class RequestedItemViewModel {
    //<editor-fold desc="properties">
    public final ObservableField<String> postId;
    public final ObservableField<String> productName;
    public final ObservableField<String> numberOffer;
    public final ObservableField<String> bestPrice;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> lastOfferMinute;
    public final ObservableField<String> imageUrl;
    public final RequestedItemListener requestedItemListener;
    //</editor-fold>

    public RequestedItemViewModel(PostViewModel post, RequestedItemListener requestedItemListener) {
        postId = new ObservableField<>(String.valueOf(post.getId()));
        productName = new ObservableField<>(post.getProductName());
        createdDate = new ObservableField<>(post.getDateCreated());
        bestPrice = new ObservableField<>();

        String offerMinute = "";
        String number = "Chưa có đề nghị nào";
        if (post.getNumberOffer() != 0) {

            offerMinute = TimeAgo.toDuration(post.getLastOfferMinute());
            long bestPricee = (long) (post.getBestPrice() + 0);
            bestPrice.set("Giá tốt nhất: " + CommonUtils.formatPrice(bestPricee));
            number = post.getNumberOffer() + " đề nghị";
        }
        lastOfferMinute = new ObservableField<>(offerMinute);
        numberOffer = new ObservableField<>(number);
        imageUrl = new ObservableField<>(BASE_URL + post.getImageUrl());
        this.requestedItemListener = requestedItemListener;
    }

    public void onItemClick() {
        requestedItemListener.onItemClick();
    }

    public void deleteItem() {
        requestedItemListener.deleteItem();
    }

    public void updateItem() {
        requestedItemListener.updateItem();
    }

    public interface RequestedItemListener {
        void onItemClick();

        void deleteItem();

        void updateItem();
    }
}
