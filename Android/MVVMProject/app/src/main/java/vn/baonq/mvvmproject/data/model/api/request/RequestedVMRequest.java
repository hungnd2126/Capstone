package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class RequestedVMRequest {
    @Expose
    @SerializedName("Type")
    private int type;

    public RequestedVMRequest(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
