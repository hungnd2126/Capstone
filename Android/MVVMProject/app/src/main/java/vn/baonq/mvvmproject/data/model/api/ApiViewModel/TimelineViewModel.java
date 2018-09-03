package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.baonq.mvvmproject.data.model.api.reponse.BaseResponse;

public class TimelineViewModel implements Serializable,Cloneable {
    @SerializedName("Status")
    @Expose
    private int Status;
    @SerializedName("DateCreated")
    @Expose
    private String  DateCreated;
    @SerializedName("OrderId")
    @Expose
    private int OrderId;
    @SerializedName("Message")
    @Expose
    private String  Message;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Longitude")
    @Expose
    private double Longitude;
    @SerializedName("Latitude")
    @Expose
    private double Latitude;

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public TimelineViewModel() {
    }

    public TimelineViewModel(int status, String dateCreated, int orderId) {
        Status = status;
        DateCreated = dateCreated;
        OrderId = orderId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return (TimelineViewModel) super.clone();
        } catch (CloneNotSupportedException e) {
            return new TimelineViewModel( Status,
            DateCreated,
            OrderId ,
            Message ,
            Description,
            Longitude,
            Latitude);
        }
    }

    public TimelineViewModel(int status, String dateCreated, int orderId, String message, String description, double longitude, double latitude) {
        Status = status;
        DateCreated = dateCreated;
        OrderId = orderId;
        Message = message;
        Description = description;
        Longitude = longitude;
        Latitude = latitude;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }
}
