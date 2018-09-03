package vn.baonq.mvvmproject.ui.main.more;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class MoreModule {

    @Provides
    MoreViewModel provideMoreViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new MoreViewModel(dataManager, schedulerProvider);
    }
}
