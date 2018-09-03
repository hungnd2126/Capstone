package vn.baonq.mvvmproject.ui.main.more.phone;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class PhoneActivityModule {

    @Provides
    PhoneViewModel providePhoneViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new PhoneViewModel(dataManager, schedulerProvider);
    }
}
