package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import vn.baonq.mvvmproject.data.model.db.UserDB;

public final class UpdateInformationRequest {

    @Expose
    @SerializedName("Image")
    public String imageFile;

    @Expose
    @SerializedName("Lastname")
    public String Lastname;

    @Expose
    @SerializedName("Bio")
    public String Bio;

    @Expose
    @SerializedName("Phone")
    public String Phone;

    @Expose
    @SerializedName("Address")
    public String Address;

    @Expose
    @SerializedName("Tknl")
    public String Tknl;

    @Expose
    @SerializedName("GeoId")
    public String GeoId;

    public UpdateInformationRequest(String image, UserDB user){
        this.imageFile = image;
        this.Lastname = user.getName();
        this.Bio = user.getBio();
        this.Phone = user.getPhone();
        this.Address = user.getAddress();
        this.GeoId = user.getGeoId();
        this.Tknl = user.getTknl();
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getBio() {
        return Bio;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGeoId() {
        return GeoId;
    }

    public void setGeoId(String geoId) {
        GeoId = geoId;
    }

    public String getTknl() {
        return Tknl;
    }

    public void setTknl(String tnkl) {
        Tknl = tnkl;
    }
}
