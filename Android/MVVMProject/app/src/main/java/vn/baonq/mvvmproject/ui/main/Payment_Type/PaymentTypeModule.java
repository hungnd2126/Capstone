package vn.baonq.mvvmproject.ui.main.Payment_Type;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class PaymentTypeModule {
    @Provides
    PaymentTypeViewModel deliveryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new PaymentTypeViewModel(dataManager, schedulerProvider);
    }
}
