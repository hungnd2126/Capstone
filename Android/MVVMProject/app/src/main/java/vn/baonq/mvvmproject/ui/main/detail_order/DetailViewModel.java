package vn.baonq.mvvmproject.ui.main.detail_order;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.data.model.api.request.GetListOfferOnPostRequest;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.ActivityDetailOrderBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferAdapter;
import vn.baonq.mvvmproject.utils.CommonUtils;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class DetailViewModel extends BaseViewModel<DetailNavigator> {
    public final ObservableField<String> shippingFee = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> ImageUrl = new ObservableField<>();
    public final ObservableField<String> dateCreated = new ObservableField<>();
    public final ObservableField<String> deliveryDate = new ObservableField<>();
    public final ObservableField<String> deliveryTo = new ObservableField<>();
    public final ObservableField<String> buyFrom = new ObservableField<>();
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> OfferSize = new ObservableField<>();
    public final ObservableField<String> Username = new ObservableField<>();
    public final ObservableField<String> UserAvartar = new ObservableField<>();
    public final ObservableField<String> deposit = new ObservableField<>();
    public final ObservableField<Integer> id = new ObservableField<>();
    public final ObservableField<String> domain = new ObservableField<>();
    public final ObservableField<String> url = new ObservableField<>();
    private final MutableLiveData<List<GetListOfferOnPostResponse.OfferResponse>> listMutableLiveData;
    public final ObservableList<GetListOfferOnPostResponse.OfferResponse> acceptOfferModels = new ObservableArrayList<>();

    public DetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listMutableLiveData = new MutableLiveData<>();

    }

    public void addAcceptOfferItem(List<GetListOfferOnPostResponse.OfferResponse> models) {
        acceptOfferModels.clear();
        for (GetListOfferOnPostResponse.OfferResponse o : models
                ) {
            if (o.getType() == 1){
                acceptOfferModels.add(0,o);
            }
        }
    }

    public ObservableList<GetListOfferOnPostResponse.OfferResponse> getAcceptOfferList() {
        return acceptOfferModels;
    }

    @BindingAdapter({"detail_offer_adapter"})
    public static void adAcceptOfferItems(RecyclerView recyclerView, List<GetListOfferOnPostResponse.OfferResponse> items) {
        DetailOfferItemAdapter adapter = (DetailOfferItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public void setRequested(int id) {
        getCompositeDisposable().add(getDataManager()
                .doGetListOfferOnPost(new GetListOfferOnPostRequest(id))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                            if (response.getListItem() != null && response.getListItem().size() > 0) {
                                listMutableLiveData.setValue(response.getListItem());
                                Log.d("here", "get done");
                            }
                            ProcessBar.endProgress();

                        }, throwable -> {
                            ProcessBar.endProgress();
                            Log.d("Error", throwable.toString());
                        }
                )
        );
    }

    public MutableLiveData<List<GetListOfferOnPostResponse.OfferResponse>> getAcceptOfferLiveData() {
        return listMutableLiveData;
    }

    public void onClickPay() {
        getNavigator().onClickPay();
    }

    public void sendMessage() {
        getNavigator().sendMessage();
    }

    public void getPostDetail(int id, ActivityDetailOrderBinding binding) {
        getCompositeDisposable().add(getDataManager()
                .getPostDetail(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                            if (response != null) {
                                if (response.getDomain() != null && !response.getDomain().equals("")) {
                                    binding.domain.setVisibility(View.VISIBLE);
                                    binding.url.setVisibility(View.VISIBLE);
                                }
                                updateDetailViewModel(response);

                            }
                            ProcessBar.endProgress();
                        }, throwable -> {
                            Log.d("Error", "");
                            ProcessBar.endProgress();
                        }
                )
        );
    }

    public void updateDetailViewModel(PostViewModel post) {
        productName.set(post.getProductName());
        shippingFee.set(CommonUtils.formatPrice(post.getShippingFee()));
        quantity.set(String.valueOf(post.getQuantity()));
        price.set(CommonUtils.formatPrice(post.getPrice()));
        description.set(post.getDescription());
        ImageUrl.set(ApiEndPoint.BASE_URL + "/" + post.getImageUrl());
        dateCreated.set(post.getDateCreated());
        domain.set(post.getDomain());
        url.set(post.getUrl());
        buyFrom.set(post.getBuyFrom());
        deliveryTo.set(post.getDeliveryTo());
        OfferSize.set(post.getOfferSize());
        Username.set(post.getNickname());
        UserAvartar.set(ApiEndPoint.BASE_URL + "/" + post.getUserAvartar());
        deposit.set(CommonUtils.formatPrice(post.getDeposit()));
        deliveryDate.set(post.getDeliveryDate());
        id.set(post.getId());
    }

}
