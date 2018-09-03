package vn.baonq.mvvmproject.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("id")
    private String geonameid;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("subcountry")
    private String subcountry;

    public City(String country, String geonameid, String name, String subcountry) {
        this.country = country;
        this.geonameid = geonameid;
        this.name = name;
        this.subcountry = subcountry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(String geonameid) {
        this.geonameid = geonameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubcountry() {
        return subcountry;
    }

    public void setSubcountry(String subcountry) {
        this.subcountry = subcountry;
    }

    @Override
    public String toString() {
        return name +", " + country;
    }
}
