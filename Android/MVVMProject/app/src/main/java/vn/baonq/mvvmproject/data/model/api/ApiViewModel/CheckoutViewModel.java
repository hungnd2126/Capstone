package vn.baonq.mvvmproject.data.model.api.ApiViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.baonq.mvvmproject.data.model.api.reponse.BaseResponse;

public class CheckoutViewModel extends BaseResponse implements Serializable {

    @SerializedName("OrderId")
    @Expose
    private int OrderId;
    @SerializedName("TransactionId")
    @Expose
    private int TransactionId;
    @SerializedName("Deposit")
    @Expose
    private int Deposit;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }
}
