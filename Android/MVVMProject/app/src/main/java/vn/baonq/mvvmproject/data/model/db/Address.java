package vn.baonq.mvvmproject.data.model.db;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("City_name")
    private String city_name;

    @Expose
    @SerializedName("Country_name")
    private String country_name;

    @Expose
    @SerializedName("Country_code")
    private String country_code;

    public Address(String address, String city_name, String country_name, String country_code) {
        this.name = address;
        this.city_name = city_name;
        this.country_name = country_name;
        this.country_code = country_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String address) {
        this.name = address;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    @Override
    public String toString() {
        String result = "";
        if (name != null && !name.equals("")) {
            result = name;
        }
        if (city_name != null && !city_name.equals("")) {
            result += "," + city_name;
        }
        if (country_name != null && !country_name.equals("")) {
            result += "," + country_name;
        }
        return result;
    }

    public String toStringCountry() {
        return country_name;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
