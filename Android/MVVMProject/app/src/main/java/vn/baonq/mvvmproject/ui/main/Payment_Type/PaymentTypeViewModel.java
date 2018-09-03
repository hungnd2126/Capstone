package vn.baonq.mvvmproject.ui.main.Payment_Type;

import android.databinding.ObservableField;

import java.util.Observable;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class PaymentTypeViewModel extends BaseViewModel<PaymentTypeNavigator> {
    public final ObservableField<String> Total = new ObservableField<>();
    public final ObservableField<String> TotalTranfer = new ObservableField<>();

    public PaymentTypeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void onSelected(int selected){getNavigator().onSelected(selected);}
    public void onPay(){getNavigator().onPay();}

}
