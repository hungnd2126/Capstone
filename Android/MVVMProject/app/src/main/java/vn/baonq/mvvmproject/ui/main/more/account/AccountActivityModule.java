package vn.baonq.mvvmproject.ui.main.more.account;

import dagger.Module;
import dagger.Provides;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AccountActivityModule {
    @Provides
    AccountViewModel provideAccountViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AccountViewModel(dataManager, schedulerProvider);
    }
}
