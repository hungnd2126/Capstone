package vn.baonq.mvvmproject.ui.main.order.Transit;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class TransitViewModel extends BaseViewModel<TransitNavigator> {
    private final ObservableList<PostViewModel> transitObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public TransitViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void addTransitItem(List<PostViewModel> posts) {
        transitObservableList.clear();
        transitObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getTransitObservableList() {
        return transitObservableList;
    }

    public void fetchTransit() {
        getCompositeDisposable().add(getDataManager()
                .doGetPostByUser(new RequestedVMRequest(1))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response.getListRequested() != null && response.getListRequested().size() > 0) {
                        postLiveData.setValue(response.getListRequested());
                    }
                }, throwable -> {
                    Log.d("fetchTransit : Error", throwable.toString());
                }));
    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }
}