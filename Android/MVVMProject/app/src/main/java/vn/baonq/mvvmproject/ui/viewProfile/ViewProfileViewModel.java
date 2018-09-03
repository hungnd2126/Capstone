package vn.baonq.mvvmproject.ui.viewProfile;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.ReviewViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.AppError;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class ViewProfileViewModel extends BaseViewModel<ViewProfileNavigator> {
    public static final String TAG = ViewProfileViewModel.class.getSimpleName();
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<Integer> buyer_score = new ObservableField<>();
    public ObservableField<Integer> traveler_score = new ObservableField<>();
    public ObservableField<String> dateCreated = new ObservableField<>();
    public ObservableField<String> bio = new ObservableField<>();
    public ObservableField<String> buyerCount = new ObservableField<>();
    public ObservableField<String> travelerCount = new ObservableField<>();

    private final ObservableList<ReviewViewModel> reviewObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<ReviewViewModel>> reviewLiveData;

    public ViewProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        reviewLiveData = new MutableLiveData<>();
    }

    public void callData(String userId){
        getCompositeDisposable().add(getDataManager()
                .getUserProfile(userId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        imageUrl.set(BASE_URL + response.getImageUrl());
                        name.set(response.getName());
                        buyer_score.set(Double.valueOf(response.getBuyerScore()).intValue());
                        traveler_score.set(Double.valueOf(response.getTravelerScore()).intValue());
                        dateCreated.set("Tham gia từ " + CommonUtils.toDateFormat(response.getDateCreated(), "MM/yyyy"));
                        bio.set(response.getBio());
                        buyerCount.set("(" + response.getBuyerCount() + ")");
                        travelerCount.set("("+ response.getTravelerCount() +")");
                    }
                }, throwable -> {
                    Log.d(TAG, "Error");
                }));
    }

    public void getData(String userId){
        getCompositeDisposable().add(getDataManager()
                .getRating(userId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.size() > 0){
                        reviewLiveData.setValue(response);
                    }
                }, throwable -> {
                    Log.d(TAG, "Error");
                }));
    }


    public void addReviewItem(List<ReviewViewModel> posts) {
        reviewObservableList.clear();
        reviewObservableList.addAll(posts);
    }

    public ObservableList<ReviewViewModel> getReviewObservableList() {
        return reviewObservableList;
    }

    public MutableLiveData<List<ReviewViewModel>> getReviewLiveData() {
        return reviewLiveData;
    }
}
