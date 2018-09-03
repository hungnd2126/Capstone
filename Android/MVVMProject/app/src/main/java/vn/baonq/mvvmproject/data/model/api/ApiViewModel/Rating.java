package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {
    @Expose
    @SerializedName("UserRatedId")
    private String UserId;

    @Expose
    @SerializedName("Point")
    private int point;

    @Expose
    @SerializedName("Comment")
    private String comment;

    @Expose
    @SerializedName("OrderId")
    private int orderId;

    @Expose
    @SerializedName("Type")
    private int type;

    public Rating(String userId, int point, String comment, int orderId, int type) {
        UserId = userId;
        this.point = point;
        this.comment = comment;
        this.orderId = orderId;
        this.type = type;
    }
}
