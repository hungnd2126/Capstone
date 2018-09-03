package vn.baonq.mvvmproject.ui.main.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageFrieBase implements Serializable {

    @SerializedName("Message")
    @Expose
    String Message;
    @SerializedName("SenderId")
    @Expose
    String SenderId;
    @SerializedName("Avartar_URL")
    @Expose
    String Avartar_URL;
    @SerializedName("Sender_Name")
    @Expose
    String Sender_Name;
    @SerializedName("Create_Date")
    @Expose
    String Create_Date;
    @SerializedName("Type")
    @Expose
    int Type;
    public MessageFrieBase(){}

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public MessageFrieBase(String message, String senderId, String avartar_URL, String sender_Name, String create_Date, int type) {
        Message = message;
        SenderId = senderId;
        Avartar_URL = avartar_URL;
        Sender_Name = sender_Name;
        Create_Date = create_Date;
        Type = type;
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

    public String getAvartar_URL() {
        return Avartar_URL;
    }

    public void setAvartar_URL(String avartar_URL) {
        Avartar_URL = avartar_URL;
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
}
