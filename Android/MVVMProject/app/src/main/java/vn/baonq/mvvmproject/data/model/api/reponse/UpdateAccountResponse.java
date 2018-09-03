package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAccountResponse extends BaseResponse {

    @Expose
    @SerializedName("Email")
    public String Email;

    @Expose
    @SerializedName("Newpass")
    public String Newpass;

    @Expose
    @SerializedName("Confirmpass")
    public String Confirmpass;

    public String getEmail() {
        return Email;
    }

    public String getNewpass() {
        return Newpass;
    }

    public String getConfirmpass() {
        return Confirmpass;
    }
}
