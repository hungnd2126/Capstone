package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class LoginRequest {
    private LoginRequest(){

    }
    public static class ServerLoginRequest {

        @Expose
        @SerializedName("Email")
        private String Email;

        @Expose
        @SerializedName("Uid")
        private String Uid;

        @Expose
        @SerializedName("Firstname")
        private String Firstname;

        @Expose
        @SerializedName("Lastname")
        private String Lastname;

        @Expose
        @SerializedName("Avatar")
        private String Avatar;

        @Expose
        @SerializedName("AccessToken")
        private String AccessToken;

        @Expose
        @SerializedName("Type")
        private String Type;

        public ServerLoginRequest(String Email, String Uid, String Fristname, String Lastname, String Avatar, String AccessToken, String type){
            this.Email = Email;
            this.Uid = Uid;
            this.Firstname = Fristname;
            this.Lastname = Lastname;
            this.Avatar = Avatar;
            this.AccessToken = AccessToken;
            this.Type = type;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            ServerLoginRequest that = (ServerLoginRequest) object;

            return Email != null ? !Email.equals(that.Email) : that.Email != null;
        }

        @Override
        public int hashCode() {
            int result = Email != null ? Email.hashCode() : 0;
            return result;
        }

        public String getAvatar() {
            return Avatar;
        }

        public String getLastname() {
            return Lastname;
        }

        public String getAccessToken() {
            return AccessToken;
        }

        public String getEmail() {
            return Email;
        }

        public String getFristname() {
            return Firstname;
        }

        public String getUid() {
            return Uid;
        }
    }

    public static class LoginFormRequest {

        @Expose
        @SerializedName("Email")
        private String Email;

        @Expose
        @SerializedName("Password")
        private String Password;


        public LoginFormRequest(String email, String password) {
            Email = email;
            Password = password;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }
    }
}
