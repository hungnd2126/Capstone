package vn.baonq.mvvmproject.ui.addtrip;

import android.content.Context;
import android.util.Log;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.CreateTripRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class AddTripViewModel extends BaseViewModel<AddTripNavigator> {


    public AddTripViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onSubmitClick() {
        getNavigator().submitForm();
    }

    public void getTime1() {
        getNavigator().onClickDateTimePicker(1);
    }

    public void getTime2() {
        getNavigator().onClickDateTimePicker(2);
    }

    public void CreateTrip(String name, String fromCityGeonameId, String toCityGeonameId, String fromDate, String toDate) {
        CreateTripRequest request = new CreateTripRequest(name, fromCityGeonameId, toCityGeonameId, fromDate, toDate);
        getCompositeDisposable().add(getDataManager()
                .doCreateTripApiCall(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().openSTMActivity();
                    ProcessBar.endProgress();
                }, throwable -> {
                    Log.d("Add Trip" , "Error" );
                    ProcessBar.endProgress();
                }));
    }

}
