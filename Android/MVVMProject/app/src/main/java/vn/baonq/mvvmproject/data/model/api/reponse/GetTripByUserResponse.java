package vn.baonq.mvvmproject.data.model.api.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public final class GetTripByUserResponse extends BaseResponse {
    @Expose
    @SerializedName("listItem")
    private List<TripItemResponse> listItem;

    public List<TripItemResponse> getListItem() {
        return listItem;
    }

    public static class TripItemResponse implements Serializable {

        @Expose
        @SerializedName("TripId")
        private int TripId;

        @Expose
        @SerializedName("Name")
        private String Name;

        @Expose
        @SerializedName("CreatedDate")
        private String CreatedDate;

        @Expose
        @SerializedName("TravelDate")
        private String TravelDate;

        @Expose
        @SerializedName("NumberOrder")
        private int NumberOrder;

        @Expose
        @SerializedName("Earning")
        private double Earning;

        public int getTripId() {
            return TripId;
        }

        public String getName() {
            return Name;
        }

        public String getCreatedDate() {
            if (CreatedDate != null) {
                try {
                    SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                    Date date = format1.parse(CreatedDate);
                    return TimeAgo.toDuration(date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return CreatedDate;
        }

        public int getNumberOrder() {
            return NumberOrder;
        }

        public double getEarning() {
            return Earning;
        }

        public String getTravelDate() {
            if (TravelDate != null) {
                try {
                    SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                    Date date = format1.parse(TravelDate);
                    return TimeAgo.toDuration(date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return TravelDate;
        }
    }

}
