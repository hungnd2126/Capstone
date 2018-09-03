package vn.baonq.mvvmproject.ui.main.home_buyer_offer;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class BuyerOfferViewModel extends BaseViewModel<BuyerOfferNavigator> {
    public static final String TAG = BuyerOfferViewModel.class.getSimpleName();
    public final ObservableList<PostViewModel> transitObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<PostViewModel>> buyerOfferLiveData;

    public BuyerOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        buyerOfferLiveData = new MutableLiveData<>();
    }


    public void addItems(List<PostViewModel> items) {
        transitObservableList.clear();
     /*   for (TransitModel item : posts) {
            Log.d("setLiveData", item.getAuthor());
        }*/
        transitObservableList.addAll(items);
    }

    public ObservableList<PostViewModel> getBuyerOfferObservableList() {
        return transitObservableList;
    }

    public void fecthPost(int ToCityId, int FromCityId) {
        // call api here
        getCompositeDisposable().add(getDataManager()
                .getPostByTrip(ToCityId, FromCityId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        buyerOfferLiveData.setValue(response);
                    }
                }, throwable -> {
                    Log.e(TAG, "fecthPostError");
                }));
    }

    public void buyerMakeOffer(int postId, int tripId) {
        getCompositeDisposable().add(getDataManager()
                .createOfferOfBuyer(postId, tripId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().showMessage("Gửi đề nghị thành công", true);
                }, throwable -> {
                    getNavigator().showMessage("Gửi đề nghị thất bại", false);
                    Log.e(TAG, "buyerMakeOfferError");
                }));
    }

    @BindingAdapter({"buyeroffer_adapter"})
    public static void addBuyerOfferItems(RecyclerView recyclerView, List<PostViewModel> items) {
        BuyerOfferAdapter adapter = (BuyerOfferAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public MutableLiveData<List<PostViewModel>> getBuyerOfferLiveData() {
        return buyerOfferLiveData;
    }
}
