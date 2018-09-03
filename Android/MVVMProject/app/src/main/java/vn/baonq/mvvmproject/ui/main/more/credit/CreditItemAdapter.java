package vn.baonq.mvvmproject.ui.main.more.credit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.databinding.TransactionItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;

public class CreditItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<HistoryViewModel> postList;
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TransactionItemBinding transactionItemBinding = TransactionItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new CreditItemViewHolder(transactionItemBinding);

    }

    public CreditItemAdapter(List<HistoryViewModel> postList) {
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

    public void addItems(List<HistoryViewModel> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class CreditItemViewHolder extends BaseViewHolder {
        private TransactionItemBinding binding;
        private CreditItemViewModel viewModel;
        private int position;

        public CreditItemViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0) {
                this.position = position;
                HistoryViewModel post = postList.get(position);
                viewModel = new CreditItemViewModel(post);
                binding.setViewModel(viewModel);
                binding.executePendingBindings();
            }
        }
    }
}
