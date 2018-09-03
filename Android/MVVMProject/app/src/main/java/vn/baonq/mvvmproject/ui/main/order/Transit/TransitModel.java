package vn.baonq.mvvmproject.ui.main.order.Transit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.baonq.mvvmproject.data.model.db.Item;

public class TransitModel {
    @Expose
    @SerializedName("Id")
     int Id;

    @SerializedName("traveler_id")
    String traveler_id;

    @SerializedName("avartar_url")
    String avartar_url;

    @SerializedName("name_traveler")
    String name_traveler ;

    @SerializedName("name_product")
    String name_product;

    @SerializedName("price")
    String price;

    @SerializedName("back_date")
    String back_date;

    public TransitModel(int id, String traveler_id, String avartar_url, String name_traveler, String name_product, String price, String back_date) {
        Id = id;
        this.traveler_id = traveler_id;
        this.avartar_url = avartar_url;
        this.name_traveler = name_traveler;
        this.name_product = name_product;
        this.price = price;
        this.back_date = back_date;
    }

    public TransitModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return false;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTraveler_id() {
        return traveler_id;
    }

    public void setTraveler_id(String traveler_id) {
        this.traveler_id = traveler_id;
    }

    public String getAvartar_url() {
        return avartar_url;
    }

    public void setAvartar_url(String avartar_url) {
        this.avartar_url = avartar_url;
    }

    public String getName_traveler() {
        return name_traveler;
    }

    public void setName_traveler(String name_traveler) {
        this.name_traveler = name_traveler;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBack_date() {
        return back_date;
    }

    public void setBack_date(String back_date) {
        this.back_date = back_date;
    }
}
