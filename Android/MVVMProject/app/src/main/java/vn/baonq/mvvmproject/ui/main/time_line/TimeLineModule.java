package vn.baonq.mvvmproject.ui.main.time_line;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.ViewModelProviderFactory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class TimeLineModule {
    @Provides
    TimeLineViewModel timeLineViewModel (DataManager dataManager, SchedulerProvider schedulerProvider){
        return new TimeLineViewModel(dataManager, schedulerProvider);
    }

    @Provides
    TimeLineAdapter timeLineAdapter(TimeLineActivity activity){
        return new TimeLineAdapter(new ArrayList<>(), activity.getApplicationContext());
    }

    @Provides
    ViewModelProvider.Factory provideTransitViewModel(TimeLineViewModel timeLineViewModel) {
        return new ViewModelProviderFactory<>(timeLineViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(TimeLineActivity activity) {
        return new LinearLayoutManager(activity.getApplicationContext());
    }
}
