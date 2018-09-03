package vn.baonq.mvvmproject.ui.main.home;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    Context context;

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        postLiveData = new MutableLiveData<>();
        getData();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void viewDetail() {
        getNavigator().viewDetail();
    }

    private final ObservableList<PostViewModel> requestedObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<PostViewModel>> postLiveData;

    public void addPosts(List<PostViewModel> posts) {
        requestedObservableList.clear();
        requestedObservableList.addAll(posts);
    }

    public ObservableList<PostViewModel> getPostObservableList() {
        return requestedObservableList;
    }

    @BindingAdapter({"loadUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_shopping);
        }


    }

    @BindingAdapter({"home_adapter"})
    public static void addPostItems(RecyclerView recyclerView, List<PostViewModel> items) {
        HomeAdapter adapter = (HomeAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(items);
        }
    }

    public void getData() {
//        ProcessBar.runProgress(context);
        getCompositeDisposable().add(getDataManager()
                .getAllPost()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    postLiveData.setValue(response);
                    ProcessBar.endProgress();
                }, throwable -> {
                    ProcessBar.endProgress();

                }));
        //  postLiveData.setValue(list);
    }


    public MutableLiveData<List<PostViewModel>> getPostLiveData() {
        return postLiveData;
    }

    public void onCreateTrip() {
        getNavigator().onCreateTrip();
    }


}
