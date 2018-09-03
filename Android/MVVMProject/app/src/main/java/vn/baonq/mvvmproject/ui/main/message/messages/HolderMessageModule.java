package vn.baonq.mvvmproject.ui.main.message.messages;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.main.message.dialogs.DialogViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class HolderMessageModule {

    @Provides
    HolderMessageViewModel holderMessageViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        return new HolderMessageViewModel(dataManager, schedulerProvider);}
}
