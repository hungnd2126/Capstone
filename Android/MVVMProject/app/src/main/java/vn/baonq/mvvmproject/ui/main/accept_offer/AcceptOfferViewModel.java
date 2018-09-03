package vn.baonq.mvvmproject.ui.main.accept_offer;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse.OfferResponse;
import vn.baonq.mvvmproject.data.model.api.request.GetListOfferOnPostRequest;
import vn.baonq.mvvmproject.data.model.db.Item;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class AcceptOfferViewModel extends BaseViewModel<AcceptOfferNavigator> {

    public final ObservableList<OfferResponse> acceptOfferModels = new ObservableArrayList<>();
    public final ObservableField<String> productImage = new ObservableField<>();
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> buyFrom = new ObservableField<>();
    public final ObservableField<String> deliveryTo = new ObservableField<>();

    private final MutableLiveData<List<OfferResponse>> listMutableLiveData;

    public AcceptOfferViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();
    }

    public void addAcceptOfferItem(List<OfferResponse> models, int type) {
        acceptOfferModels.clear();
        for (int i = 0; i < models.size(); i++){
            if (models.get(i).getType() == type){
                acceptOfferModels.add(models.get(i));
            }
        }
    }

    public ObservableList<OfferResponse> getAcceptOfferList() {
        return acceptOfferModels;
    }


    public void setProductData(String name, String image, String buyFrom, String deliveryTo) {
        productImage.set(image);
        productName.set(name);
        this.buyFrom.set(buyFrom);
        this.deliveryTo.set(deliveryTo);
    }

    public void setRequested(int id) {
        // call api here
        getCompositeDisposable().add(getDataManager()
                .doGetListOfferOnPost(new GetListOfferOnPostRequest(id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                            if (response.getListItem() != null && response.getListItem().size() > 0) {
                                listMutableLiveData.setValue(response.getListItem());
                            }
                        }, throwable -> {
                            Log.d("Error", throwable.toString());
                        }
                )
        );
    }

    public void changeOffer(int i) {
        getNavigator().changeOffer(i);
    }

    public MutableLiveData<List<OfferResponse>> getAcceptOfferLiveData() {
        return listMutableLiveData;
    }

    public void onViewDetailClick() {
        getNavigator().onViewDetailClick();
    }


}
