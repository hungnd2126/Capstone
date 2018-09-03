package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.baonq.mvvmproject.data.model.api.reponse.BaseResponse;
import vn.baonq.mvvmproject.utils.TimeAgo;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;

public class HistoryViewModel{
    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("CreateDate")
    private String createDate;

    @Expose
    @SerializedName("Value")
    private String value;

    @Expose
    @SerializedName("Dau")
    private String dau;

    @Expose
    @SerializedName("IsSuccess")
    private String isSuccess;

    public String getName() {
        return name;
    }

    public String getCreateDate() {
        if (createDate != null) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT);
                Date date = format1.parse(createDate);
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                return format2.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createDate;
    }

    public String getValue() {
        return value;
    }

    public String getDau() {
        return dau;
    }

    public String getIsSuccess() {
        return isSuccess;
    }
}
