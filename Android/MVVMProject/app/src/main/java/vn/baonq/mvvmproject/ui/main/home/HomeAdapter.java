package vn.baonq.mvvmproject.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.HomeItemBinding;
import vn.baonq.mvvmproject.databinding.LayoutHomeEmptyBinding;
import vn.baonq.mvvmproject.ui.addtrip.AddTripActivity;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;

public class HomeAdapter  extends RecyclerView.Adapter<BaseViewHolder> {

    private List<PostViewModel> postList;
    private Context context;

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_EMPTY){
            LayoutHomeEmptyBinding layoutHomeEmptyBinding= LayoutHomeEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
             return new HomeEmptyViewHolder(layoutHomeEmptyBinding);
        }else {
            HomeItemBinding homeItemBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new HomeViewHolder(homeItemBinding);
        }
    }

    public HomeAdapter(List<PostViewModel> postList, Context context) {
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
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (postList != null && postList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addItems(List<PostViewModel> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class HomeViewHolder extends BaseViewHolder implements HomeItemViewModel.HomeItemListener {
        private HomeItemBinding binding;
        private HomeItemViewModel homeItemViewModel;
        private int position;

        public HomeViewHolder(HomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0 && position >= 0) {
                this.position = position;
                homeItemViewModel = new HomeItemViewModel(postList.get(position), this);
                if(postList.get(position).getDeposit()== 0){
                    binding.deposit.setVisibility(View.GONE);
                }
                binding.setViewModel(homeItemViewModel);
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
            Intent intent = new Intent(context, DeliveryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post",postList.get(position));
            intent.putExtra("data",bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
        public class HomeEmptyViewHolder extends BaseViewHolder implements HomeItemViewModel.HomeItemListener {
            private LayoutHomeEmptyBinding binding;
            private HomeItemViewModel homeItemViewModel;

            public HomeEmptyViewHolder(LayoutHomeEmptyBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
            @Override
            public void onBind(int position) {
              //  homeItemViewModel = new HomeItemViewModel(null, this);
                binding.btnAddTrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = AddTripActivity.newIntent(context);
                        context.startActivity(intent);
                    }
                }) ;
                binding.executePendingBindings();
            }
            @Override
            public void viewDetail() {

            }

            @Override
            public void makeOffer() {
            }


        }
}