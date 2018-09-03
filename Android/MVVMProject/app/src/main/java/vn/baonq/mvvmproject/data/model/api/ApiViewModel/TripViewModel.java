package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public class TripViewModel implements Serializable {

    @SerializedName("TripId")
    @Expose
    public String TripId;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("FromCity")
    @Expose
    public String FromCity ;
    @SerializedName("ToCity")
    @Expose
    public String ToCity ;
    @SerializedName("FromCityId")
    @Expose
    public int FromCityId ;
    @SerializedName("ToCityId")
    @Expose
    public int ToCityId ;
    @SerializedName("FromDate")
    @Expose
    public String FromDate ;
    @SerializedName("ToDate")
    @Expose
    public String ToDate ;
    @SerializedName("DateCreated")
    @Expose
    public String DateCreated ;
    @SerializedName("DateUpdated")
    @Expose
    public String DateUpdated ;
    @SerializedName("TravelerName")
    @Expose
    public String TravelerName ;
    @SerializedName("TravelerAvartar")
    @Expose
    public String TravelerAvartar ;
    @SerializedName("TravelerId")
    @Expose
    public String TravelerId ;

    public int getFromCityId() {
        return FromCityId;
    }

    public void setFromCityId(int fromCityId) {
        FromCityId = fromCityId;
    }

    public int getToCityId() {
        return ToCityId;
    }

    public void setToCityId(int toCityId) {
        ToCityId = toCityId;
    }

    public String getTravelerName() {
        return TravelerName;
    }

    public String getTripId() {
        return TripId;
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }

    public void setTravelerName(String travelerName) {
        TravelerName = travelerName;
    }

    public String getTravelerAvartar() {
        return TravelerAvartar;
    }

    public void setTravelerAvartar(String travelerAvartar) {
        TravelerAvartar = travelerAvartar;
    }

    public String getTravelerId() {
        return TravelerId;
    }

    public void setTravelerId(String travelerId) {
        TravelerId = travelerId;
    }

    public TripViewModel() {
    }

    public TripViewModel(String name, String fromCity, String toCity, String fromDate, String toDate, String dateCreated, String dateUpdated) {
        Name = name;
        FromCity = fromCity;
        ToCity = toCity;
        FromDate = fromDate;
        ToDate = toDate;
        DateCreated = dateCreated;
        DateUpdated = dateUpdated;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String fromCity) {
        FromCity = fromCity;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getDateCreated() {
        if (DateCreated != null) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                Date date = format1.parse(DateCreated);
                return TimeAgo.toDuration(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        DateUpdated = dateUpdated;
    }
}
