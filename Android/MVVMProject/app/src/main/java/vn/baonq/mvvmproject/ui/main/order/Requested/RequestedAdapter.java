package vn.baonq.mvvmproject.ui.main.order.Requested;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.RequestedItemBinding;
import vn.baonq.mvvmproject.ui.addpost.AddPostActivity;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferActivity;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public class RequestedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<PostViewModel> postList;
    private Context context;
    private RequestedViewModel viewModel;


    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RequestedItemBinding requestedItemBinding = RequestedItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new RequestedViewHolder(requestedItemBinding);

    }

    public RequestedAdapter(List<PostViewModel> postList, Context context) {
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

    public void setViewModel(RequestedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public class RequestedViewHolder extends BaseViewHolder implements RequestedItemViewModel.RequestedItemListener {
        private RequestedItemBinding binding;
        private RequestedItemViewModel mRequestedViewModel;
        private int position;

        public RequestedViewHolder(RequestedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0) {
                this.position = position;
                PostViewModel post = postList.get(position);
                binding.swipe.addDrag(SwipeLayout.DragEdge.Left, binding.loUpdate);
                binding.swipe.addDrag(SwipeLayout.DragEdge.Right, binding.loDelete);
                mRequestedViewModel = new RequestedItemViewModel(post, this);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    Date current = Calendar.getInstance().getTime();
                    Date date = format.parse(post.getDeliveryDate().toString());
                    if(current.getTime()>date.getTime()){

                        binding.backgroundTimeline.setVisibility(View.VISIBLE);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                binding.setViewModel(mRequestedViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void onItemClick() {
            Intent intent = AcceptOfferActivity.newIntent(context);
            PostViewModel item = postList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", item);
            intent.putExtra("Post",bundle);
            context.startActivity(intent);
        }

        @Override
        public void deleteItem() {
            if (viewModel != null){
                viewModel.deleleItem(postList.get(position).getId());
            }
            binding.swipe.toggle();
            postList.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public void updateItem() {
            Intent intent = AddPostActivity.newIntent(context);
            intent.putExtra("Post",postList.get(position));
            intent.putExtra("callFrom", "updateItem");
            context.startActivity(intent);
        }
    }

}
