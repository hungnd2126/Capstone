package vn.baonq.mvvmproject.ui.singleTripManage;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class STMActivityModule {
    @Provides
    STMViewModel stmViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new STMViewModel(dataManager, schedulerProvider);
    }

    @Provides
    STMPagerAdapter stmPagerAdapter(STMActivity stmActivity){
        return new STMPagerAdapter(stmActivity.getSupportFragmentManager());
    }
}
