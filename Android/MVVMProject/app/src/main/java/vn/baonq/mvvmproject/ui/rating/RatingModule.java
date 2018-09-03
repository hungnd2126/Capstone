package vn.baonq.mvvmproject.ui.rating;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class RatingModule {

    @Provides
    RatingViewModel provideRatingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new RatingViewModel(dataManager, schedulerProvider);
    }
}
