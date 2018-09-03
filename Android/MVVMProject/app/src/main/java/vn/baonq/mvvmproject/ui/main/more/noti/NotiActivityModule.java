package vn.baonq.mvvmproject.ui.main.more.noti;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class NotiActivityModule {

    @Provides
    NotiViewModel provideNotiViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new NotiViewModel(dataManager, schedulerProvider);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(NotiActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    NotiItemAdapter provideNotiItemAdapter(){
        return new NotiItemAdapter(new ArrayList<>());
    }
}
