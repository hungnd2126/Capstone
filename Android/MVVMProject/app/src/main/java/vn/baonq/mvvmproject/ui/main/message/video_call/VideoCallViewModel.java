package vn.baonq.mvvmproject.ui.main.message.video_call;

import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.GetListOfferOnPostRequest;
import vn.baonq.mvvmproject.data.remote.ApiEndPoint;
import vn.baonq.mvvmproject.databinding.IncomingBinding;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.BindingUtils;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class VideoCallViewModel extends BaseViewModel {
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();
    public VideoCallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void getUser(String  id, IncomingBinding binding) {
        // call api here
        getCompositeDisposable().add(getDataManager()
                .getUser(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        response -> {
                           username.set(response.NickName);
                            BindingUtils.setImageUrl(binding.layoutItemAvartar, ApiEndPoint.BASE_URL+response.UserAvartar);
                        }, throwable -> {
                            Log.d("Error", throwable.toString());
                        }
                )
        );
    }
}
