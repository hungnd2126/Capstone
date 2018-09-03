
package vn.baonq.mvvmproject.ui.main.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IDialog;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.baonq.mvvmproject.utils.TimeAgo;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements Serializable {
    @SerializedName("Id")
    @Expose
    String Id;
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
    private String LastMessage;

//    @SerializedName("ListMessage")
//    @Expose
//    private List<MessageFrieBase> listMessage;

    public Dialog() {
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getCreate_Date(){
        if(Create_Date!=null){
            try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date date = format1.parse(Create_Date);
            return TimeAgo.toDuration(date.getTime());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.LastMessage = lastMessage;
    }
}
