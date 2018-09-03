package vn.baonq.mvvmproject.ui.main.message.dialogs;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.ui.main.message.model.Dialog;

public class DialogItemViewModel {
    public final ObservableField<String> id = new ObservableField<>();
    public final ObservableField<String> avartar_url= new ObservableField<>();
    public final ObservableField<String> dialogName = new ObservableField<>();
    public final ObservableField<String> lastMessage= new ObservableField<>();
    public final ObservableField<String> lastMessage_create= new ObservableField<>();
    public final ObservableField<Integer> unread= new ObservableField<>();
    public final DialogItemViewModel.DialogItemListener dialogItemListener;

    private int unreadCount;
    public DialogItemViewModel(Dialog dialog,String senderId,DialogItemViewModel.DialogItemListener dialogItemListener) {

        if(senderId.equals(dialog.getSenderId())){
            id.set(dialog.getId());
            avartar_url.set(ApiEndPoint.BASE_URL+dialog.getReceiverAvartar());
            dialogName.set(dialog.getReceiverName());
            lastMessage.set(dialog.getLastMessage());

            lastMessage_create.set(dialog.getCreate_Date());
            unread.set(dialog.getUnreadCount());
        }else{
            id.set(dialog.getId());
            avartar_url.set(ApiEndPoint.BASE_URL+dialog.getSenderAvartar());
            dialogName.set(dialog.getSender_Name());
            lastMessage.set(dialog.getLastMessage());
            lastMessage_create.set(dialog.getCreate_Date());
            unread.set(dialog.getUnreadCount());
        }


        this.dialogItemListener= dialogItemListener;
    }
    public void onItemClick() { dialogItemListener.onItemClick();}

    public interface DialogItemListener{
        void onItemClick();
    }

}
