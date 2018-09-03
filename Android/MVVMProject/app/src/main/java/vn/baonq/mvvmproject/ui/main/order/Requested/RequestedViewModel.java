package vn.baonq.mvvmproject.ui.main.order.Requested;

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

public class RequestedViewModel extends BaseViewModel<RequestedNavigator> {
    private final ObservableList<PostViewModel> requestedObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public RequestedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();

    }


    public void addRequestedItem(List<PostViewModel> posts) {
        requestedObservableList.clear();
        requestedObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getRequestedObservableList() {
        return requestedObservableList;
    }

    public void fetchRequested() {
        getCompositeDisposable().add(getDataManager()
                .doGetPostByUser(new RequestedVMRequest(0))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getListRequested() != null && response.getListRequested().size() > 0){
                        postLiveData.setValue(response.getListRequested());
                    }
                }, throwable -> {
                    Log.d("Error", "RequestedVM");
                }));
    }

    public void deleleItem(int id){
        getCompositeDisposable().add(getDataManager()
                .deletePostApiCall(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response, throwable) -> {
                }));
    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }
}
