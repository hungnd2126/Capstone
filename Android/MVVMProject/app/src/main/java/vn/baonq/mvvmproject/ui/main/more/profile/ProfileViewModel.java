package vn.baonq.mvvmproject.ui.main.more.profile;

import android.databinding.ObservableField;

import java.io.File;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.UpdateInformationRequest;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> display_name = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> bio = new ObservableField<>();
    public ObservableField<String> tknl = new ObservableField<>();

    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setupInfo(UserDB user) {
        imageUrl.set(BASE_URL + user.getImageUrl());
        display_name.set(user.getName());
        email.set(user.getEmail());
        phone.set(user.getPhone());
        bio.set(user.getBio());
        address.set(user.getAddress());
        tknl.set(user.getTknl());
    }

    public void onClickAddress() {
        getNavigator().showAddressDialog();
    }

    public void onClickSave() {
        getNavigator().saveProfile();
    }

    public void onUploadClick() {
        getNavigator().onUploadImage();
    }

    public void UpdateInfo(String image, UserDB user) {
        UpdateInformationRequest request = new UpdateInformationRequest(image, user);

        DataManager.LoggedInMode mode = DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT;
        if (getDataManager().getCurrentUserLoggedInMode() == 1)
            mode = DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE;
        else if (getDataManager().getCurrentUserLoggedInMode() == 2)
            mode = DataManager.LoggedInMode.LOGGED_IN_MODE_FB;
        else if (getDataManager().getCurrentUserLoggedInMode() == 3)
            mode = DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER;
        DataManager.LoggedInMode finalMode = mode;


        getCompositeDisposable().add(getDataManager()
                .doUpdateInfomationApiCall(request)
                .doOnSuccess(response -> {
                    user.setName(response.getLastname());
                    user.setAddress(response.getAddress());
                    user.setImageUrl(response.getImageFile());
                    user.setPhone(response.getPhone());
                    user.setBio(response.getBio());
                    user.setTknl(response.getTknl());
                    getDataManager().setCurrentUser(user);
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().updateCompleted("Cập nhật thành công");
                }, throwable -> {
                    getNavigator().updateCompleted("Cập nhật không thành công");
                }));
    }

}
