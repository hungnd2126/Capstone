package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.databinding.OfferedItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.make_offer.MakeOfferActivity;

public class OfferedAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final String TAG = OfferedAdapter.class.getSimpleName();
    private List<PostViewModel> postList;
    private Context context;
    private OfferedViewModel viewModel;
    private GetTripByUserResponse.TripItemResponse trip;
    public void setViewModel(OfferedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferedItemBinding offeredItemBinding = OfferedItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new OfferedViewHolder(offeredItemBinding);
    }

    public OfferedAdapter(List<PostViewModel> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }
    public void addTrip( GetTripByUserResponse.TripItemResponse trip){
        this.trip=trip;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (postList != null && postList.size() > 0) {
            return postList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<PostViewModel> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class OfferedViewHolder extends BaseViewHolder implements OfferedItemViewModel.OfferedItemListener {
        private OfferedItemBinding binding;
        private OfferedItemViewModel offeredItemViewModel;
        private int position;

        public OfferedViewHolder(OfferedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0 && position >= 0) {
                this.position = position;
                binding.swipe.addDrag(SwipeLayout.DragEdge.Left, binding.loUpdate);
                binding.swipe.addDrag(SwipeLayout.DragEdge.Right, binding.loDelete);
                offeredItemViewModel = new OfferedItemViewModel(postList.get(position), this);
                binding.setViewModel(offeredItemViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void viewDetail() {
            Intent intent = DetailActivity.newIntent(context);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", postList.get(position));
            bundle.putSerializable("Trip", trip);
            intent.putExtra("data", bundle);
            intent.putExtra("callFrom", "STM");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        @Override
        public void deleteOffer() {
            if (viewModel != null){
                viewModel.deleteOffer(postList.get(position).getId());
            }
            postList.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public void updateOffer() {
            Intent intent = MakeOfferActivity.newIntent(context);
            intent.putExtra("callFrom", TAG);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", postList.get(position));
            bundle.putSerializable("Trip", trip);
            intent.putExtra("data", bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
