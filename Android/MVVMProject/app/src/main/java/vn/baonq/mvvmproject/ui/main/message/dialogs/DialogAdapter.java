package vn.baonq.mvvmproject.ui.main.message.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.ItemDialogBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewHolder;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;

public class DialogAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Dialog> postList;
    private Context context;
    private String SenderId;
    private String Avatar;
    private String NickName;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDialogBinding itemDialogBinding = ItemDialogBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DialogAdapter.DialogViewHolder(itemDialogBinding);
    }

    public DialogAdapter(List<Dialog> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (postList != null && postList.size() > 0) {
            return 1;
        } else {
            return 0;
        }
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

    public void addItem(Dialog item) {
        postList.add(0, item);
        notifyDataSetChanged();
    }

    public void updateItem(Dialog item, int position) {
        postList.remove(position);
        postList.add(0, item);
        notifyDataSetChanged();
    }

    public void addSender(String Id, String Avatar, String NickName) {
        SenderId = Id;
        this.Avatar = Avatar;
        this.NickName = NickName;
    }

    public void addItems(List<Dialog> items) {
        postList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        postList.clear();
    }

    public class DialogViewHolder extends BaseViewHolder implements DialogItemViewModel.DialogItemListener {
        private ItemDialogBinding binding;
        private DialogItemViewModel mDialogViewModel;
        private int position;

        public DialogViewHolder(ItemDialogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            if (postList != null && postList.size() > 0) {
                this.position = position;
                Dialog dialog = postList.get(position);
                DialogItemViewModel dialogViewModel = new DialogItemViewModel(dialog, SenderId, this);
                //     binding.dialogDatet
                binding.setViewModel(dialogViewModel);
                binding.executePendingBindings();
            }
        }

        @Override
        public void onItemClick() {

            Intent intent = new Intent(context, CustomHolderMessagesActivity.class);
            Dialog dialog = postList.get(position);
            String receiverId;
            String receiverName;
            String receiverAvatar;
            String senderid;
            if (SenderId.equals(dialog.getSenderId())) {
                receiverId = dialog.getReceiverId();
                receiverName = dialog.getReceiverName();
                receiverAvatar = dialog.getReceiverAvartar();
                senderid = dialog.getSenderId();
            } else {
                receiverId = dialog.getSenderId();
                receiverName = dialog.getSender_Name();
                receiverAvatar = dialog.getSenderAvartar();
                senderid = dialog.getReceiverId();
            }
            intent.putExtra("receiverId", receiverId);
            intent.putExtra("receiverName", receiverName);
            intent.putExtra("receiverAvatar", receiverAvatar);

            intent.putExtra("SenderId", SenderId);
            intent.putExtra("Avatar", Avatar);
            intent.putExtra("NickName", NickName);
            intent.putExtra("callFrom","Dialog");
            context.startActivity(intent);
        }
    }
}
