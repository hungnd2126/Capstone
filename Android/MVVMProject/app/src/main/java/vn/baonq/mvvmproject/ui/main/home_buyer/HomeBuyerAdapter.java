package vn.baonq.mvvmproject.ui.main.home_buyer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TripViewModel;
import vn.baonq.mvvmproject.databinding.TripBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferAcivity;


public class HomeBuyerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<TripViewModel> modelList;
    private Context context;

    public HomeBuyerAdapter(List<TripViewModel> list, Context context) {
        this.modelList = list;
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TripBinding tripBinding = TripBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new HomeBuyerViewHolder(tripBinding);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<TripViewModel> items) {
        modelList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        modelList.clear();
    }

    @Override
    public int getItemCount() {
        if (modelList != null && modelList.size() > 0) {
            return modelList.size();
        } else {
            return 0;
        }
    }

    public class HomeBuyerViewHolder extends BaseViewHolder implements HomeBuyerItemViewModel.HomeBuyerItemListener {
        private TripBinding binding;
        private HomeBuyerItemViewModel homeBuyerItemViewModel;
        private int position;

        public HomeBuyerViewHolder(TripBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            this.position = position;
//            postList.get(position);
            TripViewModel item = modelList.get(position);
            homeBuyerItemViewModel = new HomeBuyerItemViewModel(item, this);
            binding.setViewModel(homeBuyerItemViewModel);
            binding.executePendingBindings();
        }


        @Override
        public void onItemClick() {
            Intent intent = new Intent(context, BuyerOfferAcivity.class);
            intent.putExtra("ToCityId", modelList.get(position).getToCityId());
            intent.putExtra("FromCityId", modelList.get(position).getFromCityId());
            intent.putExtra("tripId", modelList.get(position).getTripId());
            context.startActivity(intent);
        }
    }
}
