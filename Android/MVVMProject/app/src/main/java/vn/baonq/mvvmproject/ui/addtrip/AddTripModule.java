package vn.baonq.mvvmproject.ui.addtrip;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AddTripModule {
    @Provides
    AddTripViewModel addTripViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new AddTripViewModel(dataManager, schedulerProvider);
    }
}
