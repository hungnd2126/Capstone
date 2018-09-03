package vn.baonq.mvvmproject.ui.main.time_line;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;
import vn.baonq.mvvmproject.databinding.ActivityTimelineBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferAdapter;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class TimeLineViewModel extends BaseViewModel<TimeLineNavigator> {
    private final ObservableList<TimelineViewModel> timelineViewModels = new ObservableArrayList<>();
    public final MutableLiveData<List<TimelineViewModel>> listMutableLiveData;
    ActivityTimelineBinding binding;
    boolean showTracking=false;
    public TimeLineViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();
    }

    public void addTimeLineItem(List<TimelineViewModel> models) {
        timelineViewModels.clear();
        timelineViewModels.addAll(models);
    }

    public ObservableList<TimelineViewModel> getTimelineObservableList() {
        return timelineViewModels;
    }

    public void setBinding(ActivityTimelineBinding binding) {
        this.binding = binding;
    }

    public void setShowTracking(boolean showTracking) {
        this.showTracking = showTracking;
    }

    public void getTimeline(int orderId) {
        getCompositeDisposable().add(getDataManager()
                .getTimeline(orderId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.size() > 0) {
                        listMutableLiveData.setValue(response);
                        if(response.get(response.size()-1).getStatus()!=5
                                &&response.get(response.size()-1).getStatus()!=6 &&showTracking){
                            binding.tracking.setVisibility(View.VISIBLE);
                        }
                    }
                    ProcessBar.endProgress();
                }, throwable -> {
                    Log.d("getTimeline : Error", "a");
                    ProcessBar.endProgress();
                }));
    }

    @BindingAdapter({"timeline_adapter"})
    public static void addTimelineItems(RecyclerView recyclerView, List<TimelineViewModel> items) {
        TimeLineAdapter adapter = (TimeLineAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public MutableLiveData<List<TimelineViewModel>> getTimelineLiveData() {
        return listMutableLiveData;
    }
    public void updateTimeline() {
        getNavigator().updateTimeline();
    }
}
