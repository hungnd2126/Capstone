package vn.baonq.mvvmproject.data.model.db;

import com.google.gson.Gson;

import java.io.Serializable;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class UserDB implements Serializable {
    private String Uid;
    private String Name;
    private String Email;
    private String Phone;
    private String Address;
    private String GeoId;
    private String Bio;
    private String ImageUrl;
    private String tknl;

    public UserDB() {

    }

    public UserDB(String imageUrl, String name, String email, String phone, String address, String GeoId, String bio, String tknl) {
        ImageUrl = imageUrl;
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        this.GeoId = GeoId;
        Bio = bio;
        this.tknl = tknl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getTknl() {
        return tknl;
    }

    public void setTknl(String tknl) {
        this.tknl = tknl;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public UserDB fromJson(String src) {
        if (src == null) return null;
        return new Gson().fromJson(src, this.getClass());
    }
}
