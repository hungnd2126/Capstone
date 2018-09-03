package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfileViewModel implements Serializable {
    @Expose
    @SerializedName("Id")
    private String UserId;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("ImageUrl")
    private String imageUrl;

    @Expose
    @SerializedName("Bio")
    private String bio;

    @Expose
    @SerializedName("DateCreated")
    private String dateCreated;

    @Expose
    @SerializedName("BuyerScore")
    private String buyerScore;

    @Expose
    @SerializedName("TravelerScore")
    private String travelerScore;

    @Expose
    @SerializedName("BuyerCount")
    private String buyerCount;

    @Expose
    @SerializedName("TravelerCount")
    private String travelerCount;

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getBuyerScore() {
        return buyerScore;
    }

    public String getTravelerScore() {
        return travelerScore;
    }

    public String getBuyerCount() {
        return buyerCount;
    }

    public String getTravelerCount() {
        return travelerCount;
    }
}
