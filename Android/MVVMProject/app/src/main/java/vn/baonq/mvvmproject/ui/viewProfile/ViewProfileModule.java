package vn.baonq.mvvmproject.ui.viewProfile;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiActivity;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiItemAdapter;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class ViewProfileModule {
    @Provides
    ViewProfileViewModel viewProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new ViewProfileViewModel(dataManager, schedulerProvider);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(ViewProfileActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    ReviewItemAdapter provideReviewItemAdapter(){
        return new ReviewItemAdapter(new ArrayList<>());
    }
}
