
package vn.baonq.mvvmproject.ui.main.message.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;
        import com.stfalcon.chatkit.commons.models.IDialog;

        import java.util.ArrayList;
        import java.util.List;

/*
 * Created by troy379 on 04.04.17.
 */
public class DialogFireBase {
    @SerializedName("Id")
    @Expose
    String Id;
    @SerializedName("Message")
    @Expose
    String Message;
    @SerializedName("SenderId")
    @Expose
    String SenderId;
    @SerializedName("SenderAvartar")
    @Expose
    String SenderAvartar;
    @SerializedName("ReceiverAvartar")
    @Expose
    String ReceiverAvartar;
    @SerializedName("SenderName")
    @Expose
    String Sender_Name;
    @SerializedName("Create_Date")
    @Expose
    String Create_Date;
    @SerializedName("ReceiverId")
    @Expose
    String ReceiverId;
    @SerializedName("ReceiverName")
    @Expose
    String ReceiverName;
    @SerializedName("UnreadCount")
    @Expose
    private int unreadCount;
    @SerializedName("LastMessage")
    @Expose
    private String lastMessage;
    @SerializedName("ListMessage")
    @Expose
    private List<MessageFrieBase> listMessage;

    public DialogFireBase() {
    }

    public DialogFireBase(String id, String message, String senderId, String senderAvartar, String receiverAvartar, String sender_Name, String create_Date, String receiverId, String receiverName, int unreadCount, String lastMessage, List<MessageFrieBase> listMessage) {
        Id = id;
        Message = message;
        SenderId = senderId;
        SenderAvartar = senderAvartar;
        ReceiverAvartar = receiverAvartar;
        Sender_Name = sender_Name;
        Create_Date = create_Date;
        ReceiverId = receiverId;
        ReceiverName = receiverName;
        this.unreadCount = unreadCount;
        this.lastMessage = lastMessage;
        this.listMessage = listMessage;
    }

    public String getSenderAvartar() {
        return SenderAvartar;
    }

    public void setSenderAvartar(String senderAvartar) {
        SenderAvartar = senderAvartar;
    }

    public String getReceiverAvartar() {
        return ReceiverAvartar;
    }

    public void setReceiverAvartar(String receiverAvartar) {
        ReceiverAvartar = receiverAvartar;
    }

    public List<MessageFrieBase> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<MessageFrieBase> listMessage) {
        this.listMessage = listMessage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }


    public String getSender_Name() {
        return Sender_Name;
    }

    public void setSender_Name(String sender_Name) {
        Sender_Name = sender_Name;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
