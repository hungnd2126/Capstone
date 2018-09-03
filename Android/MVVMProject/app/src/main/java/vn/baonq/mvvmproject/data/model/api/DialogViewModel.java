package vn.baonq.mvvmproject.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DialogViewModel implements Serializable {
    @Expose
    @SerializedName("Username")
    public String Username ;
    @Expose
    @SerializedName("NickName")
    public String NickName ;
    @Expose
    @SerializedName("UserAvartar")
    public String UserAvartar ;
    @Expose
    @SerializedName("UserId")
    public String UserId ;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getUserAvartar() {
        return UserAvartar;
    }

    public void setUserAvartar(String userAvartar) {
        UserAvartar = userAvartar;
    }
}
