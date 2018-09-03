package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class GetListOfferOnPostRequest {
    @Expose
    @SerializedName("id")
    private int postId;

    public GetListOfferOnPostRequest(int postId){
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}
