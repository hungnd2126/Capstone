package vn.baonq.mvvmproject.ui.main.more.noti;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Notification;
import vn.baonq.mvvmproject.databinding.NotificationItemBinding;
import vn.baonq.mvvmproject.databinding.TransactionItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditItemViewModel;

public class NotiItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Notification> postList;
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        NotificationItemBinding transactionItemBinding = NotificationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new NotificationItemViewHolder(transactionItemBinding);

    }

    public NotiItemAdapter(List<Notification> postList) {
        this.postList = postList;
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

    public void addItems(List<Notification> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class NotificationItemViewHolder extends BaseViewHolder {
        private NotificationItemBinding binding;
        private NotificationItemViewModel viewModel;
        private int position;

        public NotificationItemViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0) {
                this.position = position;
                Notification post = postList.get(position);
                viewModel = new NotificationItemViewModel(post);
                binding.setViewModel(viewModel);
                binding.executePendingBindings();
            }
        }
    }
}
