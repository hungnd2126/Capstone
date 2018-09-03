package vn.baonq.mvvmproject.ui.main.more.noti;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Notification;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditViewModel;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class NotiViewModel extends BaseViewModel<NotiNavigator> {

    public static final String TAG = NotiViewModel.class.getSimpleName();
    private final ObservableList<Notification> notiObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<Notification>> notiLiveData;

    public NotiViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        notiLiveData = new MutableLiveData<>();
    }

    public void addNotiItem(List<Notification> posts) {
        notiObservableList.clear();
        notiObservableList.addAll(posts);
    }

    public ObservableList<Notification> getNotiObservableList() {
        return notiObservableList;
    }

    public void getData() {
        getCompositeDisposable().add(getDataManager()
                .getNotification()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.size() > 0) {
                        notiLiveData.setValue(response);
                    }
                }, throwable -> {
                    Log.d(TAG, "Error");
                }));
        ProcessBar.endProgress();
    }

    public MutableLiveData<List<Notification>> getNotiLiveData() {
        return notiLiveData;
    }


}
