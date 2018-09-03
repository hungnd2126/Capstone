package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.LayoutPostItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;

public class BuyerOfferAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<PostViewModel> postList;
    private Context context;
    private BuyerOfferViewModel viewModel;
    private int tripId;

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutPostItemBinding layoutPostItemBinding = LayoutPostItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new BuyerOfferAdapter.BuyerOfferViewHolder(layoutPostItemBinding);
    }

    public BuyerOfferAdapter(List<PostViewModel> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    public void setViewModel(BuyerOfferViewModel viewModel) {
        this.viewModel = viewModel;
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

    public class BuyerOfferViewHolder extends BaseViewHolder implements BuyerOfferItemViewModel.BuyerOfferItemListener {
        private LayoutPostItemBinding binding;
        private BuyerOfferItemViewModel buyerOfferItemViewModel;
        private int position;

        public BuyerOfferViewHolder(LayoutPostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
//            postList.get(position);
            PostViewModel item = postList.get(position);
            buyerOfferItemViewModel = new BuyerOfferItemViewModel(item, this);
            binding.setViewModel(buyerOfferItemViewModel);
            binding.executePendingBindings();
        }


        @Override
        public void onItemClick() {
            PostViewModel item = postList.get(position);
            viewModel.buyerMakeOffer(item.getId(), tripId);
        }
    }
}