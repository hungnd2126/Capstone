package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferViewModel implements Serializable {
    @SerializedName("Id")
    @Expose
    public int Id;
    @SerializedName("DateCreated")
    @Expose
    public String DateCreated;
    @SerializedName("DateUpdated")
    @Expose
    public String DateUpdated;
    @SerializedName("ShippingFee")
    @Expose
    public String ShippingFee;
    @SerializedName("DeliveryDate")
    @Expose
    public String DeliveryDate;
    @SerializedName("BuyerId")
    @Expose
    public String BuyerId;
    @SerializedName("Message")
    @Expose
    public String Message;
    @SerializedName("TravellerId")
    @Expose
    public String TravellerId;
    @SerializedName("PostId")
    @Expose
    public int PostId;
    @SerializedName("TripId")
    @Expose
    public int TripId;
    @SerializedName("OrderId")
    @Expose
    public int OrderId;
    @SerializedName("TravlerVote")
    @Expose
    public int TravlerVote;
    @SerializedName("TravlerName")
    @Expose
    public String TravlerName;
    @SerializedName("TravlerAvartar")
    @Expose
    public String TravlerAvartar;

    public OfferViewModel() {
    }

    public OfferViewModel(int id, String dateCreated, String dateUpdated, String shippingFee, String deliveryDate, String buyerId, String travellerId, int postId, int tripId, int orderId) {
        Id = id;
        DateCreated = dateCreated;
        DateUpdated = dateUpdated;
        ShippingFee = shippingFee;
        DeliveryDate = deliveryDate;
        BuyerId = buyerId;
        TravellerId = travellerId;
        PostId = postId;
        TripId = tripId;
        OrderId = orderId;
    }

    public int getTravlerVote() {
        return TravlerVote;
    }

    public void setTravlerVote(int travlerVote) {
        TravlerVote = travlerVote;
    }

    public String getTravlerName() {
        return TravlerName;
    }

    public void setTravlerName(String travlerName) {
        TravlerName = travlerName;
    }

    public String getTravlerAvartar() {
        return TravlerAvartar;
    }

    public void setTravlerAvartar(String travlerAvartar) {
        TravlerAvartar = travlerAvartar;
    }

    public String getShippingFee() {
        return ShippingFee;
    }

    public void setShippingFee(String shippingFee) {
        ShippingFee = shippingFee;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        DateUpdated = dateUpdated;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getTravellerId() {
        return TravellerId;
    }

    public void setTravellerId(String travellerId) {
        TravellerId = travellerId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public int getTripId() {
        return TripId;
    }

    public void setTripId(int tripId) {
        TripId = tripId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
