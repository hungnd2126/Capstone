package vn.baonq.mvvmproject.ui.addressDialog;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Rating;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class AddressViewModel extends BaseViewModel<AddressNavigator> {
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> city = new ObservableField<>();

    public AddressViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setupData(String address, String city){
    }

    public void onSubmitClick() {getNavigator().doRating();}

    public void doRatingApi(Rating rating){


    }
}
