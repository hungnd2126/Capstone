package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePhoneResponse extends BaseResponse {

    @Expose
    @SerializedName("Phone")
    public String Phone;

    public String getPhone() {
        return Phone;
    }
}
