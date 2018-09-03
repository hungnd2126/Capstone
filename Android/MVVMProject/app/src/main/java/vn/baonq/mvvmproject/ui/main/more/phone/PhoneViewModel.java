package vn.baonq.mvvmproject.ui.main.more.phone;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.UpdatePhoneRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class PhoneViewModel extends BaseViewModel<PhoneNavigator>{

    private String phone;

    public PhoneViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickSave() {
        getNavigator().savePhone();
    }

    public void UpdateInfo(String phone){
        UpdatePhoneRequest request = new UpdatePhoneRequest(phone);
        DataManager.LoggedInMode mode = DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT;
        if (getDataManager().getCurrentUserLoggedInMode() == 1) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE;
        else if (getDataManager().getCurrentUserLoggedInMode() == 2) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_FB;
        else if (getDataManager().getCurrentUserLoggedInMode() == 3) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER;
        DataManager.LoggedInMode finalMode = mode;
        getCompositeDisposable().add(getDataManager()
                .doUpdatePhoneApiCall(request)
                .doOnSuccess(response -> getDataManager().updateUserPhone(getDataManager().getAccessToken(),
                        getDataManager().getCurrentUserId(),
                        finalMode,
                        phone))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().updateCompleted("Cập nhật thành công");
                }, throwable -> {
                    getNavigator().updateCompleted("Cập nhật không thành công");
                }));
    }
}
