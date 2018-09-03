package vn.baonq.mvvmproject.ui.splash;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void startSeeding() {
       decideNextActivity();
    }

    private void decideNextActivity() {
        getNavigator().openMainActivity();
    }
}
