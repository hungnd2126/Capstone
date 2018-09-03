package vn.baonq.mvvmproject.ui.main.message.video_call;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.message.messages.HolderMessageViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class VideoCallModule {
    @Provides
    VideoCallViewModel videoCallViewModel(DataManager dataManager,
                                                  SchedulerProvider schedulerProvider) {
        return new VideoCallViewModel(dataManager, schedulerProvider);}
}

