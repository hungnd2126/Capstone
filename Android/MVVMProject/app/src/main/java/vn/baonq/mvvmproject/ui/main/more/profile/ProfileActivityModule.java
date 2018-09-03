package vn.baonq.mvvmproject.ui.main.more.profile;


import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class ProfileActivityModule {

    @Provides
    ProfileViewModel provideProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new ProfileViewModel(dataManager, schedulerProvider);
    }
}
