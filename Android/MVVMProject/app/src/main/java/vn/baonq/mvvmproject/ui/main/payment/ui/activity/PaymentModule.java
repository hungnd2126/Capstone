package vn.baonq.mvvmproject.ui.main.payment.ui.activity;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class PaymentModule {
    @Provides
    PaymentViewModel providePaymentViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new PaymentViewModel(dataManager, schedulerProvider);
    }
}
