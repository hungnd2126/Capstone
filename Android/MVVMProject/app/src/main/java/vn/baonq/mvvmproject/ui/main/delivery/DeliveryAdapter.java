package vn.baonq.mvvmproject.ui.main.delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import vn.baonq.mvvmproject.MvvmApp;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse.TripItemResponse;
import vn.baonq.mvvmproject.databinding.DeliveryItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.make_offer.MakeOfferActivity;
import vn.baonq.mvvmproject.ui.singleTripManage.STMActivity;

public class DeliveryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final String TAG = DeliveryAdapter.class.getSimpleName();
    private List<TripItemResponse> itemList;
    private Context context;
    private PostViewModel postViewModel;
    private String callFrom;

    public void setCallFrom(String callFrom) {
        this.callFrom = callFrom;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeliveryItemBinding deliveryItemBinding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DeliveryViewHolder(deliveryItemBinding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public DeliveryAdapter(List<TripItemResponse> items, Context context) {
        this.itemList = items;
        this.context = context;
        this.postViewModel = new PostViewModel();
    }

    @Override
    public int getItemCount() {
        if (itemList != null && itemList.size() > 0) {
            return itemList.size();
        } else {
            return 0;
        }
    }

    public void addPost(PostViewModel postViewModel) {
        this.postViewModel = postViewModel;
    }

    public void addItems(List<TripItemResponse> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        itemList.clear();
    }

    public class DeliveryViewHolder extends BaseViewHolder implements DeliveryItemViewModel.DeliveryItemListener {
        private DeliveryItemBinding binding;
        private DeliveryItemViewModel itemViewModel;
        private int position;

        public DeliveryViewHolder(DeliveryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (itemList != null && itemList.size() > 0) {
                this.position = position;
                TripItemResponse item = itemList.get(position);
                itemViewModel = new DeliveryItemViewModel(item, this);
                binding.setViewModel(itemViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void onItemClick() {
            if (callFrom != null && callFrom.equals("activity")) {
                Intent intent = new Intent(context, MakeOfferActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Trip", itemList.get(position));
                bundle.putSerializable("Post", postViewModel);
                intent.putExtra("data", bundle);
                intent.putExtra("callFrom", TAG);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (callFrom != null && callFrom.equals("fragment")) {
                if (itemList != null && itemList.size() > 0) {
                    Intent intent = STMActivity.newIntent(context);
                    Bundle bundle= new Bundle();
                    bundle.putSerializable("Trip",itemList.get(position));
                    intent.putExtra("data",bundle);
                    context.startActivity(intent);
                }
            }
        }
    }
}
