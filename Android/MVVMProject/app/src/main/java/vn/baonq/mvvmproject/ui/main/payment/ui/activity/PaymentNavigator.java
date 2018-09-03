package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

public interface PaymentNavigator {
    void doOnSuccess(int orderId);
    void doOnError();
    void doOnRutTienTC();
}
