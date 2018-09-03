package vn.baonq.mvvmproject.data.model.api.reponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;

public final class RequestedVMResponse extends BaseResponse {
    @Expose
    @SerializedName("ListRequested")
    private List<PostViewModel> listRequested;

    public List<PostViewModel> getListRequested() {
        return listRequested;
    }

    public static class RequestedVM implements Serializable{
        @Expose
        @SerializedName("PostId")
        private Integer postId;
        @Expose
        @SerializedName("ProductName")
        private String productName;
        @Expose
        @SerializedName("NumberOffer")
        private Integer numberOffer;
        @Expose
        @SerializedName("BestPrice")
        private Double bestPrice;
        @Expose
        @SerializedName("CreateDate")
        private String createdDate;
        @Expose
        @SerializedName("LastOfferMinute")
        private Double lastOfferMinute;
        @Expose
        @SerializedName("ImageUrl")
        private String imageUrl;
        @Expose
        @SerializedName("BuyFrom")
        private String buyFrom;
        @Expose
        @SerializedName("DeliveryTo")
        private String deliveryTo;
        @Expose
        @SerializedName("TravelerId")
        private String travelerId;
        @Expose
        @SerializedName("TravelerAvartar")
        private String travelerAvartar;
        @Expose
        @SerializedName("TravelerName")
        private String travelerName;
        @Expose
        @SerializedName("DeliveryDate")
        private String deliveryDate;
        @Expose
        @SerializedName("Deposit")
        private int deposit;
        @Expose
        @SerializedName("OfferId")
        private int offerId;
        @Expose
        @SerializedName("Shippingfree")
        private double shippingFee;

        public double getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(double shippingFee) {
            this.shippingFee = shippingFee;
        }

        public int getOfferId() {
            return offerId;
        }

        public void setOfferId(int offerId) {
            this.offerId = offerId;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public RequestedVM(Integer postId, String productName, Integer numberOffer,
                           Double bestPrice, String createdDate, Double lastOfferMinute, String imageUrl) {
            this.postId = postId;
            this.productName = productName;
            this.numberOffer = numberOffer;
            this.bestPrice = bestPrice;
            this.createdDate = createdDate;
            this.lastOfferMinute = lastOfferMinute;
            this.imageUrl = imageUrl;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public String getTravelerId() {
            return travelerId;
        }

        public String getTravelerAvartar() {
            return travelerAvartar;
        }

        public String getTravelerName() {
            return travelerName;
        }

        public Integer getPostId() {
            return postId;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getNumberOffer() {
            return numberOffer;
        }

        public Double getBestPrice() {
            return bestPrice;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public Double getLastOfferMinute() {
            return lastOfferMinute;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getBuyFrom() {
            return buyFrom;
        }

        public String getDeliveryTo() {
            return deliveryTo;
        }
    }
}
