package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.baonq.mvvmproject.data.model.db.Address;
import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public class PostViewModel implements Serializable {

    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("ProductName")
    @Expose
    private String productName;

    @SerializedName("Price")
    @Expose
    private int price;

    @SerializedName("Quantity")
    @Expose
    private int quantity;
    @SerializedName("Buy_Address")
    @Expose
    private Address Buy_Address;

    @SerializedName("Delivery_Address")
    @Expose
    private Address Delivery_Address;
    @SerializedName("BuyFrom")
    @Expose
    private String BuyFrom;

    @SerializedName("DeliveryTo")
    @Expose
    private String DeliveryTo;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("ShippingFee")
    @Expose
    private int shippingFee;

    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;

    @SerializedName("DateUpdated")
    @Expose
    private String dateUpdated;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;

    @SerializedName("Username")
    @Expose
    private String Username;

    @SerializedName("UserAvartar")
    @Expose
    private String UserAvartar;
    @SerializedName("Message")
    @Expose
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("UserId")
    @Expose
    private String userId;

    @SerializedName("BestPrice")
    @Expose
    private int BestPrice;

    @SerializedName("LastOfferMinute")
    @Expose
    private long LastOfferMinute;

    @SerializedName("NumberOffer")
    @Expose
    private int NumberOffer;

    @SerializedName("Nickname")
    @Expose
    private String Nickname;

    @SerializedName("Deposit")
    @Expose
    private int Deposit;

    @SerializedName("DeliveryDate")
    @Expose
    private String DeliveryDate;
    @SerializedName("OrderId")
    @Expose
    private int OrderId;

    @SerializedName("Timeline")
    @Expose
    private TimelineViewModel Timeline;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public TimelineViewModel getTimeline() {
        return Timeline;
    }

    public void setTimeline(TimelineViewModel timeline) {
        Timeline = timeline;
    }

    public PostViewModel(Integer id, String productName, int price, int quantity, int shippingFee, String dateCreated, String dateUpdated, String description, String imageUrl) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.shippingFee = shippingFee;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.description = description;
        ImageUrl = imageUrl;
    }

    public String getBuyFrom() {
        return BuyFrom;
    }

    public void setBuyFrom(String buyFrom) {
        BuyFrom = buyFrom;
    }

    public String getDeliveryTo() {
        return DeliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        DeliveryTo = deliveryTo;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public PostViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getDateCreated() {
        if (dateCreated != null) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                Date date = format1.parse(dateCreated);
                return TimeAgo.toDuration(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateCreated;
    }

    public Address getBuy_Address() {
        return Buy_Address;
    }

    public void setBuy_Address(Address buy_Address) {
        Buy_Address = buy_Address;
    }

    public Address getDelivery_Address() {
        return Delivery_Address;
    }

    public void setDelivery_Address(Address delivery_Address) {
        Delivery_Address = delivery_Address;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserAvartar() {
        return UserAvartar;
    }

    public void setUserAvartar(String userAvartar) {
        UserAvartar = userAvartar;
    }

    public double getBestPrice() {
        return BestPrice;
    }

    public void setBestPrice(int bestPrice) {
        BestPrice = bestPrice;
    }

    public long getLastOfferMinute() {
        return LastOfferMinute;
    }

    public void setLastOfferMinute(long lastOfferMinute) {
        LastOfferMinute = lastOfferMinute;
    }

    public int getNumberOffer() {
        return NumberOffer;
    }

    public void setNumberOffer(int numberOffer) {
        NumberOffer = numberOffer;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public String getDeliveryDate() {
        if (DeliveryDate != null) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                Date date = format1.parse(DeliveryDate);
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                return format2.format(date);
            } catch (Exception e) {
            }
        }
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getOfferSize() {
        if (NumberOffer == 0) {
            return "Chưa có thoả thuận";
        }
        return "Đã có " + NumberOffer + " thoả thuận";
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}