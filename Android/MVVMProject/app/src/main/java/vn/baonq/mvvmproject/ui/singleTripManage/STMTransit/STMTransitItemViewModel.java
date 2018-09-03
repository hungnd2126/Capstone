package vn.baonq.mvvmproject.ui.singleTripManage.STMTransit;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.view.View;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.StmtransitItemBinding;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class STMTransitItemViewModel {


    public final ObservableField<Integer> postId;
    public final ObservableField<String> productName;
    public final ObservableField<String> deposit;
    public final ObservableField<String> subsist;
    public final ObservableField<String> createdDate;
    public final ObservableField<String> imageUrl;
    public final ObservableField<String> travelerId;
    public final ObservableField<String> travelerAvartar;
    public final ObservableField<String> travelerName;
    public final ObservableField<String> deliveryDate;
    public final ObservableField<String> timeline;

    public final STMTransitItemListener stmTransitItemListener;

    public STMTransitItemViewModel(PostViewModel post, STMTransitItemListener stmTransitItemListener, StmtransitItemBinding binding){
        postId = new ObservableField<>(post.getId());
        productName = new ObservableField<>(post.getProductName());
        createdDate = new ObservableField<>(post.getDateCreated());
        deposit = new ObservableField<>(CommonUtils.formatPrice(post.getDeposit()));
        subsist = new ObservableField<>(CommonUtils.formatPrice(post.getBestPrice() - post.getDeposit()));
        imageUrl = new ObservableField<>(BASE_URL + post.getImageUrl());
        travelerId = new ObservableField<>(post.getUserId());
        travelerAvartar = new ObservableField<>(BASE_URL + post.getUserAvartar());
        travelerName = new ObservableField<>(post.getNickname());
        deliveryDate = new ObservableField<>(post.getDeliveryDate());
        timeline = new ObservableField<>();
        if (post.getTimeline() != null) {
            switch (post.getTimeline().getStatus()) {
                case 1:
                    timeline.set("Đang ở " + post.getTimeline().getDescription());
                    break;
                case 2:
                    timeline.set("Đã mua được hàng");
                    break;
                case 3:
                    timeline.set("Có thể giao hàng");
                    break;
                case 4:
                    timeline.set("Không thể mua hàng");
                    break;
                case 5:
                    timeline.set("Đơn hàng đã bị hủy");
                    binding.backgroundTimeline.setBackgroundColor(Color.parseColor("#d52029"));
                    break;
                case 6:
                    timeline.set("Giao hàng thành công");
                    break;
            }} else {
            binding.backgroundTimeline.setVisibility(View.GONE);

        }
        this.stmTransitItemListener = stmTransitItemListener;
    }

    public void onItemClick(){
        stmTransitItemListener.onItemClick();
    }
    public void onClickUser() {
        stmTransitItemListener.onClickUser();
    }

    public void sendMessage() {
        stmTransitItemListener.sendMessage();
    }
    public interface STMTransitItemListener{
        void onItemClick();

        void sendMessage();

        void onClickUser();
    }
}
