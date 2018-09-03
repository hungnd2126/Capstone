package vn.baonq.mvvmproject.ui.main.detail_order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.databinding.DetailOfferItemBinding;
import vn.baonq.mvvmproject.databinding.HomeItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.main.home.HomeItemViewModel;

public class DetailOfferItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<GetListOfferOnPostResponse.OfferResponse> offerResponses;
    private Context context;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DetailOfferItemBinding binding = DetailOfferItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DetailOfferItemViewHolder(binding);
    }

    public DetailOfferItemAdapter(List<GetListOfferOnPostResponse.OfferResponse> offerResponses, Context context) {
        this.offerResponses = offerResponses;
        this.context = context;
    }

    public void addItems(List<GetListOfferOnPostResponse.OfferResponse> items) {
        offerResponses.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        offerResponses.clear();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (offerResponses != null && offerResponses.size() > 0) {
            return offerResponses.size();
        } else {
            return 0;
        }
    }

    public class DetailOfferItemViewHolder extends BaseViewHolder implements HomeItemViewModel.HomeItemListener {
        private DetailOfferItemBinding binding;
        private DetailOfferItemViewModel detailOfferItemViewModel;
        private int position;

        public DetailOfferItemViewHolder(DetailOfferItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (offerResponses != null && offerResponses.size() > 0 && position >= 0) {
                //if (offerResponses.get(position).getType() == 1) {
                    this.position = position;
                    detailOfferItemViewModel = new DetailOfferItemViewModel(offerResponses.get(position));
                    binding.setViewModel(detailOfferItemViewModel);
                    binding.executePendingBindings();
                //}

            }
        }


        @Override
        public void viewDetail() {
            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", offerResponses.get(position));
            intent.putExtra("data", bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

        @Override
        public void makeOffer() {
            Intent intent = new Intent(context, DeliveryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", offerResponses.get(position));
            intent.putExtra("data", bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
