package vn.baonq.mvvmproject.ui.map;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class MapModule {
    @Provides
    MapViewModel mapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new MapViewModel(dataManager, schedulerProvider);
    }
}
