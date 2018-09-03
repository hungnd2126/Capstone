package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.time.Instant;
import java.util.Date;

public final class CreateTripResponse extends BaseResponse {

    @Expose
    @SerializedName("TripId")
    private int TripId;

    @Expose
    @SerializedName("DateCreated")
    private String DateCreated;

    @Expose
    @SerializedName("NumberOrder")
    private int NumberOrder;

    @Expose
    @SerializedName("Earning")
    private double Earning;

    public int getTripId() {
        return TripId;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public int getNumberOrder() {
        return NumberOrder;
    }

    public double getEarning() {
        return Earning;
    }
}
