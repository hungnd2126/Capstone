package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletViewModel {
    @Expose
    @SerializedName("Value")
    private double Value;


    public WalletViewModel(double value) {
        this.Value = value;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }
}
