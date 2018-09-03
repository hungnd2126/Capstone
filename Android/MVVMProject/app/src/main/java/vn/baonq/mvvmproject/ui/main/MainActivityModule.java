package vn.baonq.mvvmproject.ui.main;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new MainViewModel(dataManager, schedulerProvider);
    }

}
