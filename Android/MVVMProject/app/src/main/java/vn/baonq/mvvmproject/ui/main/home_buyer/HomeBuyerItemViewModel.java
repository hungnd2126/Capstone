package vn.baonq.mvvmproject.ui.main.home_buyer;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TripViewModel;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;

public class HomeBuyerItemViewModel {

    public final ObservableField<String> Name;
    public final ObservableField<String> FromCity ;
    public final ObservableField<String> ToCity ;
    public final ObservableField<String> FromDate ;
    public final ObservableField<String> ToDate ;
    public final ObservableField<String> DateCreated ;
    public final ObservableField<String> DateUpdated ;
    public final ObservableField<String> TravelerName ;
    public final ObservableField<String>TravelerAvartar ;
    public final HomeBuyerItemViewModel.HomeBuyerItemListener homeBuyerItemListener;

    public HomeBuyerItemViewModel(TripViewModel model, HomeBuyerItemListener homeBuyerItemListener) {

        Name=new ObservableField<>(model.getName());
        FromCity=new ObservableField<>(model.getFromCity());
        ToCity=new ObservableField<>(model.getToCity());
        FromDate=new ObservableField<>(model.getFromDate());
        ToDate=new ObservableField<>(model.getToDate());
        DateCreated=new ObservableField<>(model.getDateCreated());
        DateUpdated=new ObservableField<>(model.getDateUpdated());
        TravelerName=new ObservableField<>(model.getTravelerName());
        TravelerAvartar=new ObservableField<>(ApiEndPoint.BASE_URL+model.getTravelerAvartar());
        this.homeBuyerItemListener = homeBuyerItemListener;
    }

    public void onItemClick() { homeBuyerItemListener.onItemClick();}

    public interface HomeBuyerItemListener{
        void onItemClick();
    }
}
