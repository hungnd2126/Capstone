package vn.baonq.mvvmproject.ui.singleTripManage.STMTransit;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class STMTransitViewModel extends BaseViewModel<STMTransitNavigator>{

    private final ObservableList<PostViewModel> stmTransitObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public STMTransitViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void addSTMTransitItem(List<PostViewModel> posts) {
        stmTransitObservableList.clear();
        stmTransitObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getStmTransitObservableList() {
        return stmTransitObservableList;
    }

    public void fetchTransit(int tripId) {
        getCompositeDisposable().add(getDataManager()
                .doGetOfferByTrip(tripId, 2)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    postLiveData.setValue(response);
                }, throwable -> {
                    Log.d("fetchTransit : Error ", "aaa");
                }));
    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }

}
