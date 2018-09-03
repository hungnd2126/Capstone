package vn.baonq.mvvmproject.ui.payment;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class PaypalModule {
    @Provides
    PaypalViewModel paypalViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new PaypalViewModel(dataManager, schedulerProvider);
    }
}
