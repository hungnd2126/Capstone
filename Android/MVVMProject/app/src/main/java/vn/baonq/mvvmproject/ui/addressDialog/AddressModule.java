package vn.baonq.mvvmproject.ui.addressDialog;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AddressModule {

    @Provides
    AddressViewModel provideAddressViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new AddressViewModel(dataManager, schedulerProvider);
    }
}
