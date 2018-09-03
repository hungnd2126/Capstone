package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public final class UpdateInformationRespone extends BaseResponse {

    @Expose
    @SerializedName("Image")
    public String imageFile;

    @Expose
    @SerializedName("Firstname")
    public String Firstname;

    @Expose
    @SerializedName("Lastname")
    public String Lastname;

    @Expose
    @SerializedName("Bio")
    public String Bio;

    @Expose
    @SerializedName("Phone")
    private String Phone;

    @Expose
    @SerializedName("Tknl")
    private String Tknl;

    @Expose
    @SerializedName("Address")
    private String Address;

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getBio() {
        return Bio;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
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

    public String getPhone() {
        return Phone;
    }

    public String getTknl() {
        return Tknl;
    }
}
