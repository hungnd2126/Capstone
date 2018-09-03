package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.baonq.mvvmproject.utils.CommonUtils;

public class Notification implements Serializable {
    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Title")
    private String Title;

    @Expose
    @SerializedName("DateCreated")
    private String DateCreated;

    @Expose
    @SerializedName("IsRead")
    private boolean IsRead;

    public String getMessage() {
        return Message;
    }

    public String getTitle() {
        return Title;
    }

    public String getDateCreated() {
        if (DateCreated != null){
            return CommonUtils.toDateFormat(DateCreated, " HH:mm dd/MM/yyyy");
        }
        return DateCreated;
    }

    public boolean isRead() {
        return IsRead;
    }
}
