package vn.baonq.mvvmproject.ui.main.home_buyer;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TripViewModel;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferAdapter;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class HomeBuyerViewModel extends BaseViewModel {
    public final ObservableList<TripViewModel> tripObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<TripViewModel>> listMutableLiveData;

    public HomeBuyerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData=new MutableLiveData<>();
    }
    @BindingAdapter({"loadUrl"})
    public static void loadImage(ImageView imageView, String imageUrl ){
        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_shopping);
        }
    }


    public void addItem(List<TripViewModel> item) {
        tripObservableList.clear();
        tripObservableList.addAll(item);
    }

    public ObservableList<TripViewModel> getSuggestTripObservableList() {
        return tripObservableList;
    }

    public void fechSuggestTrip() {
        // call api here
        getCompositeDisposable().add(getDataManager()
                .getSuggestTrip()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        listMutableLiveData.setValue(response);
                    }
                }, throwable -> {
                }));
        ProcessBar.endProgress();
    }
    @BindingAdapter({"suggest_trip_adapter"})
    public static void addSuggestTripItems(RecyclerView recyclerView, List<TripViewModel> items) {
        HomeBuyerAdapter adapter = (HomeBuyerAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }
    public MutableLiveData<List<TripViewModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
