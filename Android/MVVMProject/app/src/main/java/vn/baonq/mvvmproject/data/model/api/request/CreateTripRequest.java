package vn.baonq.mvvmproject.data.model.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class CreateTripRequest  {
    @Expose
    @SerializedName("Name")
    private String Name;

    @Expose
    @SerializedName("FromCityGeonameId")
    private String FromCityGeonameId;

    @Expose
    @SerializedName("ToCityGeonameId")
    private String ToCityGeonameId;

    @Expose
    @SerializedName("FromDate")
    private String FromDate;

    @Expose
    @SerializedName("ToDate")
    private String ToDate;


    public CreateTripRequest(String name, String fromCityGeonameId, String toCityGeonameId, String fromDate, String toDate) {
        Name = name;
        FromCityGeonameId = fromCityGeonameId;
        ToCityGeonameId = toCityGeonameId;
        FromDate = fromDate;
        ToDate = toDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFromCityGeonameId() {
        return FromCityGeonameId;
    }

    public void setFromCityGeonameId(String fromCityGeonameId) {
        FromCityGeonameId = fromCityGeonameId;
    }

    public String getToCityGeonameId() {
        return ToCityGeonameId;
    }

    public void setToCityGeonameId(String toCityGeonameId) {
        ToCityGeonameId = toCityGeonameId;
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
}
