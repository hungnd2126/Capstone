package vn.baonq.mvvmproject.ui.addpost;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AddPostModule {
    @Provides
    AddPostViewModel addPostViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new AddPostViewModel(dataManager, schedulerProvider);
    }
}
