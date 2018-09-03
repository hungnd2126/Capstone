package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAccountRequest {

    @Expose
    @SerializedName("Email")
    public String Email;

    @Expose
    @SerializedName("Newpass")
    public String Newpass;

    @Expose
    @SerializedName("Confirmpass")
    public String Confirmpass;

    public UpdateAccountRequest(String Email, String Newpass, String ConfirmPass){
        this.Email = Email;
        this.Newpass = Newpass;
        this.Confirmpass = ConfirmPass;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNewpass() {
        return Newpass;
    }

    public void setNewpass(String newpass) {
        Newpass = newpass;
    }

    public String getConfirmpass() {
        return Confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        Confirmpass = confirmpass;
    }
}
