package vn.baonq.mvvmproject.ui.main.delivery;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse.TripItemResponse;
import vn.baonq.mvvmproject.data.model.db.Item;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;


public class DeliveryViewModel extends BaseViewModel<DeliveryNavigator> {
    public static final String TAG = DeliveryViewModel.class.getSimpleName();
    public final ObservableList<TripItemResponse> deliveryObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<TripItemResponse>> postLiveData;

    public DeliveryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void addRequestedItem(List<TripItemResponse> posts) {
        deliveryObservableList.clear();
        deliveryObservableList.addAll(posts);
    }

    public ObservableList<TripItemResponse> getDeliveryObservableList() {
        return deliveryObservableList;
    }

    public void setRequested(int postId) {
        // call api here
        getCompositeDisposable().add(getDataManager()
                .doGetTripByUser(postId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response.getListItem() != null && response.getListItem().size() > 0) {
                        postLiveData.setValue(response.getListItem());
                    }
                }, throwable -> {
                    Log.d(TAG, "Error");
                }));
    }

    public MutableLiveData<List<TripItemResponse>> getPostLiveData() {
        return postLiveData;
    }

    public void onAddTripClick() {
        getNavigator().onAddTripClick();
    }

}
