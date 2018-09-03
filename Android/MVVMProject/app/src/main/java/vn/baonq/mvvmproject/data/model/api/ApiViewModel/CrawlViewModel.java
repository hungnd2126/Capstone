package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CrawlViewModel implements Serializable {
    @SerializedName("url")
    @Expose
    public String url ;
    @SerializedName("domain")
    @Expose
    public String domain ;
    @SerializedName("productPrice")
    @Expose
    public double productPrice ;
    @SerializedName("imageURL")
    @Expose
    public String imageURL ;
    @SerializedName("productName")
    @Expose
    public String productName ;
    @SerializedName("country")
    @Expose
    public String country ;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public CrawlViewModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
