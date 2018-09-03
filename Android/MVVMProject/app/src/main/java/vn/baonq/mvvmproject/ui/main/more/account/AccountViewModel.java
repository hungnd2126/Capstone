package vn.baonq.mvvmproject.ui.main.more.account;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.UpdateAccountRequest;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class AccountViewModel extends BaseViewModel<AccountNavigator>{

    private String email, newpass, confirmpass;

    public AccountViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onClickSave() {
        getNavigator().saveAccount();
    }

    public void setAccount() {
        email = getDataManager().getCurrentUserEmail();
    }

    public String getEmail(){return email;}

    public void UpdateInfo(String email, String newpass, String confirmpass) {
        UpdateAccountRequest request = new UpdateAccountRequest(email, newpass, confirmpass);
        DataManager.LoggedInMode mode = DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT;
        if (getDataManager().getCurrentUserLoggedInMode() == 1) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE;
        else if (getDataManager().getCurrentUserLoggedInMode() == 2) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_FB;
        else if (getDataManager().getCurrentUserLoggedInMode() == 3) mode = DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER;
        DataManager.LoggedInMode finalMode = mode;
        getCompositeDisposable().add(getDataManager()
                .doUpdateAccountApiCall(request)
                .doOnSuccess(response -> getDataManager().updateUserAccount(getDataManager().getAccessToken(),
                        getDataManager().getCurrentUserId(),
                        finalMode,
                        email,
                        newpass,
                        confirmpass))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().updateCompleted("Cập nhật thành công");
                    ProcessBar.endProgress();
                }, throwable -> {
                    getNavigator().updateCompleted("Cập nhật không thành công");
                    ProcessBar.endProgress();
                }));
    }
}
