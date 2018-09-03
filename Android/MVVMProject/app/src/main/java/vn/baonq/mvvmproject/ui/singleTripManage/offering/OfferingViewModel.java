package vn.baonq.mvvmproject.ui.singleTripManage.offering;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class OfferingViewModel extends BaseViewModel<OfferingNavigator>{

    public OfferingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void viewDetail(){
        getNavigator().viewDetail();
    }
    private final ObservableList<PostViewModel> offeringObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public void addPosts(List<PostViewModel> posts) {
        offeringObservableList.clear();
        offeringObservableList.addAll(posts);
    }


    public ObservableList<PostViewModel> getOfferingObservableList() {
        return offeringObservableList;
    }

    @BindingAdapter({"loadUrl"})
    public static void loadImage(ImageView imageView, String imageUrl ){
        if (imageUrl != null) {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_shopping);
        }

    }
    @BindingAdapter({"offering_adapter"})
    public static void addOfferingItems(RecyclerView recyclerView, List<PostViewModel> items) {
        OfferingAdapter adapter = (OfferingAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public void getData(int tripId) {
        // call api here
        ArrayList list = new ArrayList<>();
         getCompositeDisposable().add(getDataManager()
                .doGetOfferByTrip(tripId,1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response!=null && response.size()>0){
                    postLiveData.setValue(response);}
                }, throwable -> {
                }));
    }

    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }

}
