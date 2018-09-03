package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.OfferingItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;

public class OfferingAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<PostViewModel> postList;
    private Context context;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferingItemBinding offeringItemBinding = OfferingItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new OfferingViewHolder(offeringItemBinding);
    }

    public OfferingAdapter(List<PostViewModel> postList, Context context) {
        this.postList = postList;
        this.context = context;
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

    public class OfferingViewHolder extends BaseViewHolder implements OfferingItemViewModel.OfferingItemListener {
        private OfferingItemBinding binding;
        private OfferingItemViewModel offeringItemViewModel;
        private int position;

        public OfferingViewHolder(OfferingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0 && position >= 0) {
                this.position = position;
                offeringItemViewModel = new OfferingItemViewModel(postList.get(position), this);
                binding.setViewModel(offeringItemViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void viewDetail() {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post",postList.get(position));
            intent.putExtra("data",bundle);
            intent.putExtra("callFrom", "homefragment");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        @Override
        public void makeOffer() {
            Intent intent = new Intent(context, PaymentActivity.class);
            context.startActivity(intent);

        }
    }

}
