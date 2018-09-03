package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public class GetListOfferOnPostResponse extends BaseResponse implements Serializable {

    @Expose
    @SerializedName("ListItem")
    private List<OfferResponse> listItem;

    public List<OfferResponse> getListItem() {
        return listItem;
    }

    public static class OfferResponse implements Serializable {

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public double getCurrent_money() {
            return current_money;
        }

        public void setCurrent_money(double current_money) {
            this.current_money = current_money;
        }

        @Expose
        @SerializedName("Status_code")
        private String statusCode;

        @Expose
        @SerializedName("Current_Money")
        private double current_money;


        @Expose
        @SerializedName("OfferId")
        private int offerId;

        @Expose
        @SerializedName("TravelerId")
        private String travelerId;

        @Expose
        @SerializedName("TravelerName")
        private String travelerName;

        @Expose
        @SerializedName("TravelerImage")
        private String travelerImage;

        @Expose
        @SerializedName("CreatedDate")
        private String createdDate;

        @Expose
        @SerializedName("DeliveryDate")
        private String deliveryDate;

        @Expose
        @SerializedName("Rate")
        private float rating;

        @Expose
        @SerializedName("TravelTo")
        private String travelTo;
        @Expose
        @SerializedName("TravelFrom")
        private String travelFrom;

        @Expose
        @SerializedName("ProductPrice")
        private int productPrice;

        @Expose
        @SerializedName("ShippingFee")
        private int shippingFee;
        @Expose
        @SerializedName("Total")
        private String Total;
        @Expose
        @SerializedName("PostId")
        private int PostId;
        @Expose
        @SerializedName("Quantity")
        private int Quantity;
        @Expose
        @SerializedName("Deposit")
        private int deposit;
        @Expose
        @SerializedName("Type")
        private int type;

        @Expose
        @SerializedName("Message")
        private String Message;

        public String getTravelFrom() {
            return travelFrom;
        }

        public void setTravelFrom(String travelFrom) {
            this.travelFrom = travelFrom;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public void setOfferId(int offerId) {
            this.offerId = offerId;
        }

        public void setTravelerId(String travelerId) {
            this.travelerId = travelerId;
        }

        public void setTravelerName(String travelerName) {
            this.travelerName = travelerName;
        }

        public void setTravelerImage(String travelerImage) {
            this.travelerImage = travelerImage;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public void setTravelTo(String travelTo) {
            this.travelTo = travelTo;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public void setShippingFee(int shippingFee) {
            this.shippingFee = shippingFee;
        }

        public int getPostId() {
            return PostId;
        }

        public void setPostId(int postId) {
            PostId = postId;
        }

        public String getTotal() {
            return Total;
        }

        public void setTotal(String total) {
            Total = total;
        }

        public int getOfferId() {
            return offerId;
        }

        public String getTravelerId() {
            return travelerId;
        }

        public String getTravelerName() {
            return travelerName;
        }

        public String getCreatedDate() {
            if (createdDate != null) {
                try {
                    SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                    Date date = format1.parse(createdDate);
                    return TimeAgo.toDuration(date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return createdDate;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public float getRating() {
            return rating;
        }

        public String getTravelTo() {
            return travelTo;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public int getShippingFee() {
            return shippingFee;
        }

        public String getTravelerImage() {
            return travelerImage;
        }

    }

}
