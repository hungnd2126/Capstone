package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCreditResponse extends BaseResponse{

    @Expose
    @SerializedName("Credit")
    public String Credit;

    public String getCredit() {
        return Credit;
    }
}
