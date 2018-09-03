package vn.baonq.mvvmproject.ui.singleTripManage.offered;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class OfferedViewModel extends BaseViewModel<OfferedNavigator> {

    public OfferedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
    }

    public void viewDetail(){
        getNavigator().viewDetail();
    }

    private final ObservableList<PostViewModel> offeredObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public void addPosts(List<PostViewModel> posts) {
        offeredObservableList.clear();
        offeredObservableList.addAll(posts);
    }


    public ObservableList<PostViewModel> getOfferedObservableList() {
        return offeredObservableList;
    }

    @BindingAdapter({"loadUrl"})
    public static void loadImage(ImageView imageView, String imageUrl ){
        if (imageUrl != null) {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_shopping);
        }

    }
    @BindingAdapter({"offered_adapter"})
    public static void addOfferedItems(RecyclerView recyclerView, List<PostViewModel> items) {
        OfferedAdapter adapter = (OfferedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public void getData(int tripId, Context context) {
      //  ProcessBar.runProgress(context);

        getCompositeDisposable().add(getDataManager()
                .doGetOfferByTrip(tripId, 0)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response!=null &&response.size()>0){
                   postLiveData.setValue(response);
               //    ProcessBar.endProgress();
                        }
                }, throwable -> {
                }));

    }
    public void deleteOffer(int postId){
        getCompositeDisposable().add(getDataManager()
        .deleteOfferApi(postId)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(response -> {

        }, throwable -> {
            Log.e("Delete Offer", "Error");
        }));
    }
    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }

}
