package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewViewModel {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("ImageUrl")
    private String imageUrl;

    @Expose
    @SerializedName("DateCreated")
    private String dateCreated;

    @Expose
    @SerializedName("Message")
    private String message;

    @Expose
    @SerializedName("ProductName")
    private String productName;

    @Expose
    @SerializedName("Score")
    private int score;


    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getMessage() {
        return message;
    }

    public String getProductName() {
        return productName;
    }

    public int getScore() {
        return score;
    }
}
