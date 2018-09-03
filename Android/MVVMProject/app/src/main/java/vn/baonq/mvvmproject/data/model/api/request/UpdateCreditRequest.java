package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCreditRequest {

    @Expose
    @SerializedName("Credit")
    public String Credit;

    public UpdateCreditRequest(String Credit) {
        this.Credit = Credit;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }
}
