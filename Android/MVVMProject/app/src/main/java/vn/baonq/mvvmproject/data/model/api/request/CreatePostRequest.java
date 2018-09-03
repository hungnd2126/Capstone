package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class CreatePostRequest implements Serializable {
    @Expose
    @SerializedName("Id")
    private int Id;
    @Expose
    @SerializedName("ProductName")
    private String productName;
    @Expose
    @SerializedName("Price")
    private double price;
    @Expose
    @SerializedName("Quantity")
    private int quantity;
    @Expose
    @SerializedName("BuyFromGeoId")
    private String buyFromGeoId;
    @Expose
    @SerializedName("DeliveryToGeoId")
    private String deliveryToGeoId;
    @Expose
    @SerializedName("ShippingFee")
    private double shippingFee;
    @Expose
    @SerializedName("ReceivedDate")
    private String receivedDate;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("Image")
    private String image;
    @Expose
    @SerializedName("Deposit")
    private String deposit;

    public CreatePostRequest() {

    }

    public CreatePostRequest(String productName, double price, int quantity, String buyFromGeoId,
                             String deliveryToGeoId, double shippingFee, String description, String image, String receivedDate) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.receivedDate = receivedDate;
        this.buyFromGeoId = buyFromGeoId;
        this.deliveryToGeoId = deliveryToGeoId;
        this.shippingFee = shippingFee;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBuyFromGeoId() {
        return buyFromGeoId;
    }

    public void setBuyFromGeoId(String buyFromGeoId) {
        this.buyFromGeoId = buyFromGeoId;
    }

    public String getDeliveryToGeoId() {
        return deliveryToGeoId;
    }

    public void setDeliveryToGeoId(String deliveryToGeoId) {
        this.deliveryToGeoId = deliveryToGeoId;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
