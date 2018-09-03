package vn.baonq.mvvmproject.ui.main.more;

import android.databinding.ObservableField;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.ui.login.LoginActivity;
import vn.baonq.mvvmproject.ui.login.LoginFacebook;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

import static vn.baonq.mvvmproject.data.DataManager.LoggedInMode.LOGGED_IN_MODE_FB;
import static vn.baonq.mvvmproject.data.DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE;
import static vn.baonq.mvvmproject.data.DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

public class MoreViewModel extends BaseViewModel<MoreNavigator> {
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public MoreViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        UserDB user = getDataManager().getCurrentUser();
        imageUrl.set(BASE_URL + user.getImageUrl());
        name.set(user.getName());
        email.set(user.getEmail());
    }

    public void onClickProfile(){
        getNavigator().openProfile();
    }

    public void onClickAccount(){
        getNavigator().openAccount();
    }

    public void onClickPhone() { getNavigator().openPhone(); }

    public void onClickCredit(){ getNavigator().openCredit();}

    public void onClickNoti(){
        getNavigator().openNoti(); }



    public void onClickLogout() {
        int loggedMode = getDataManager().getCurrentUserLoggedInMode();
        if (loggedMode == LOGGED_IN_MODE_FB.getType()){
            LoginFacebook.logoutFb();
        } else if (loggedMode == LOGGED_IN_MODE_GOOGLE.getType()){
        } else if (loggedMode == LOGGED_IN_MODE_SERVER.getType()){
        }

        getDataManager().setUserAsLoggedOut();
        getNavigator().openLoginActivity();
    }
}
