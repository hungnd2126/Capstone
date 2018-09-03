package vn.baonq.mvvmproject.ui.singleTripManage.STMReceived;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class STMReceivedViewModel extends BaseViewModel<STMReceivedNavigator>{

    private final ObservableList<PostViewModel> stmReceivedObservableList = new ObservableArrayList<>();
    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public STMReceivedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void addSTMReceivedItem(List<PostViewModel> posts) {
        stmReceivedObservableList.clear();
        stmReceivedObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getStmReceivedObservableList() {
        return stmReceivedObservableList;
    }

    public void fetchReceived(int tripId) {
        getCompositeDisposable().add(getDataManager()
                .doGetOfferByTrip(tripId, 3)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.size() > 0){
                        postLiveData.setValue(response);
                    }
                }, throwable -> {
                    Log.d("fetchReceived : Error ", throwable.getMessage().toString() );
                }));
    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }
}
