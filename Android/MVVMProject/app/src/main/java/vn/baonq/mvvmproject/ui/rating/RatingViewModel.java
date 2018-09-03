package vn.baonq.mvvmproject.ui.rating;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Rating;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class RatingViewModel extends BaseViewModel<RatingNavigator> {
    public RatingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onSubmitClick() {
        getNavigator().doRating();
    }

    public void doRatingApi(Rating rating) {
        getCompositeDisposable().add(getDataManager()
                .doCreateRatingApi(rating)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().dismissDialog();
                }, throwable -> {
                    getNavigator().dismissDialog();
                }));

    }
}
