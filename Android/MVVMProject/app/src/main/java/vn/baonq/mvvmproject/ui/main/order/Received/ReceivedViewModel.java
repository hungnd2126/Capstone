package vn.baonq.mvvmproject.ui.main.order.Received;

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

public class ReceivedViewModel extends BaseViewModel<ReceivedNavigator>{
    public static final String TAG = ReceivedViewModel.class.getSimpleName();
    private final ObservableList<PostViewModel> receivedObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public ReceivedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();

    }

    public void addReceivedItem(List<PostViewModel> posts) {
        receivedObservableList.clear();
        receivedObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getReceivedObservableList() {
        return receivedObservableList;
    }

    public void fetchReceived() {
        getCompositeDisposable().add(getDataManager()
                .doGetPostByUser(new RequestedVMRequest(2))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response.getListRequested() != null && response.getListRequested().size() > 0){
                        postLiveData.setValue(response.getListRequested());
                    }
                }, throwable -> {
                     Log.d(TAG, "Error" );
                }));

    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }
}
