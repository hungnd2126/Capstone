package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class LoginResponse extends BaseResponse{

    @Expose
    @SerializedName("AccessToken")
    private String accessToken;

    @Expose
    @SerializedName("Avatar")
    private String avatar;

    @Expose
    @SerializedName("Email")
    private String Email;

    @Expose
    @SerializedName("Uid")
    private String Uid;

    @Expose
    @SerializedName("Lastname")
    private String Lastname;

    @Expose
    @SerializedName("Amount")
    private double Amount;

    @Expose
    @SerializedName("Phone")
    private String Phone;

    @Expose
    @SerializedName("Bio")
    private String Bio;

    @Expose
    @SerializedName("Tknl")
    private String Tknl;

    @Expose
    @SerializedName("Address")
    private String Address;

    @Expose
    @SerializedName("GeoId")
    private String GeoId;

    public double getAmount() { return Amount; }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getUid() {
        return Uid;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getBio() {
        return Bio;
    }

    public String getAddress() {
        return Address;
    }

    public String getGeoId() {
        return GeoId;
    }

    public String getTknl() {
        return Tknl;
    }
}
