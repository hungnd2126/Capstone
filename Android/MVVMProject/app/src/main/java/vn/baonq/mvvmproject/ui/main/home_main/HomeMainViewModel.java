package vn.baonq.mvvmproject.ui.main.home_main;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class HomeMainViewModel extends BaseViewModel {
    public HomeMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
