package vn.baonq.mvvmproject.ui.main.message.messages;

import android.arch.lifecycle.MutableLiveData;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class HolderMessageViewModel extends BaseViewModel {
    public HolderMessageViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
