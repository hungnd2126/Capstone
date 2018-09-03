package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @Expose
    @SerializedName("Message")
    private String message;

    @Expose
    @SerializedName("Status_code")
    private String statusCode;

    @Expose
    @SerializedName("Current_Money")
    private double current_money;

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public double getCurrent_money() {
        return current_money;
    }
}
