package vn.baonq.mvvmproject.ui.main.order;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class OrderViewModel extends BaseViewModel<OrderNavigator> {
    public OrderViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
/*
    public void onAddOrderClick() {getNavigator().onAddOrderClick();}

    public void onRatingClick() {getNavigator().showRatingDialog();}*/
}
