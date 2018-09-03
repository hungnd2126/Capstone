package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.databinding.StmreceivedItemBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.complete_offer.CompleteOfferActivity;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferActivity;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;

public class STMReceivedAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<PostViewModel> postList;
    private Context context;
    private String SenderId;
    private String Avatar;
    private String NickName;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        StmreceivedItemBinding stmReceivedItemBinding = StmreceivedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new STMReceivedViewHolder(stmReceivedItemBinding);
    }

    public STMReceivedAdapter(List<PostViewModel> postList, Context context){
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
    public void addSender(String Id, String Avatar, String NickName) {
        SenderId= Id;
        this.Avatar=  Avatar;
        this.NickName= NickName;
    }
    public void addItems(List<PostViewModel> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class STMReceivedViewHolder extends BaseViewHolder implements STMReceivedItemViewModel.STMReceivedItemListener {
        private StmreceivedItemBinding binding;
        private STMReceivedItemViewModel mSTMReceivedViewModel;
        private int position;

        public STMReceivedViewHolder(StmreceivedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position){
            if (postList != null && postList.size() > 0) {
                this.position = position;
                PostViewModel post = postList.get(position);
                mSTMReceivedViewModel = new STMReceivedItemViewModel(post, this);
                binding.setViewModel(mSTMReceivedViewModel);
//                binding.executePendingBindings();
            }
        }

        @Override
        public void onItemClick(){
            Intent intent = new Intent(context,CompleteOfferActivity.class);
            PostViewModel item = postList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Post", item);
            intent.putExtra("Data",bundle);
            intent.putExtra("callFrom", "STMReceived");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        @Override
        public void sendMessage() {
            Intent intent= new Intent(context,CustomHolderMessagesActivity.class);
            intent.putExtra("receiverId", postList.get(position).getUserId());
            intent.putExtra("receiverName", postList.get(position).getNickname());
            intent.putExtra("receiverAvatar", postList.get(position).getUserAvartar());

            intent.putExtra("SenderId",SenderId);
            intent.putExtra("Avatar",Avatar);
            intent.putExtra("NickName",NickName);
            context.startActivity(intent);
        }
    }



}
