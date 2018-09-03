package vn.baonq.mvvmproject.ui.login;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import vn.baonq.mvvmproject.data.AppDataManager;
import vn.baonq.mvvmproject.data.AppDataManager_Factory;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.api.request.LoginRequest;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.di.module.AppModule_ProvideDataManagerFactory;
import vn.baonq.mvvmproject.ui.base.BaseViewModel;
import vn.baonq.mvvmproject.utils.ProcessBar;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void login(String email, String password) {
        getNavigator().loginForm(email, password);
    }

    public void loginClick() {
        getNavigator().login();
    }

    public void loginGGClick() {
        getNavigator().onSignInClick();
    }

    public void loginFbClick() {
        getNavigator().onFbClick();
    }

    public void loginFb(HashMap<String, String> profileUser) {
        getCompositeDisposable().add(getDataManager()
                .doLoginFbApiCall(new LoginRequest.ServerLoginRequest(
                        profileUser.get("email"),
                        profileUser.get("id"),
                        profileUser.get("first_name"),
                        profileUser.get("last_name"),
                        profileUser.get("avatar"),
                        profileUser.get("token"),
                        "fb")
                ).doOnSuccess(response -> {
                    UserDB user = new UserDB();
                    user.setUid(response.getUid());
                    user.setName(response.getLastname());
                    user.setGeoId(response.getGeoId());
                    user.setAddress(response.getAddress());
                    user.setImageUrl(response.getAvatar());
                    user.setEmail(response.getEmail());
                    user.setPhone(response.getPhone());
                    user.setBio(response.getBio());
                    user.setTknl(response.getTknl());
                    getDataManager().setCurrentUser(user);
                    getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_FB);
                    getDataManager().setAccessToken(response.getAccessToken());
                    getDataManager().setAmout(response.getAmount());
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            setIsLoading(false);
                            getNavigator().openMainActivity();
                            ProcessBar.endProgress();

                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                            ProcessBar.endProgress();
                        }
                )
        );
    }

    public void registerUserGG(GoogleSignInAccount account) {
        getCompositeDisposable().add(getDataManager()
                .doLoginFbApiCall(new LoginRequest.ServerLoginRequest(
                        account.getEmail(),
                        account.getId(),
                        account.getGivenName(),
                        account.getFamilyName(),
                        account.getPhotoUrl().toString(),
                        account.getIdToken(),
                        "gg")
                ).doOnSuccess(response -> {
                    UserDB user = new UserDB();
                    user.setUid(response.getUid());
                    user.setName(response.getLastname());
                    user.setGeoId(response.getGeoId());
                    user.setAddress(response.getAddress());
                    user.setImageUrl(response.getAvatar());
                    user.setEmail(response.getEmail());
                    user.setPhone(response.getPhone());
                    user.setBio(response.getBio());
                    user.setTknl(response.getTknl());
                    getDataManager().setCurrentUser(user);

                    getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE);
                    getDataManager().setAccessToken(response.getAccessToken());
                    getDataManager().setAmout(response.getAmount());

                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            setIsLoading(false);
                            getNavigator().openMainActivity();
                            ProcessBar.endProgress();
                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                            ProcessBar.endProgress();
                        }
                )
        );
    }

    public void loginForm(String email, String password) {
        getCompositeDisposable().add(getDataManager()
                .doLoginFormApiCall(new LoginRequest.LoginFormRequest(email, password)
                ).doOnSuccess(response -> {
                    UserDB user = new UserDB();
                    user.setUid(response.getUid());
                    user.setName(response.getLastname());
                    user.setGeoId(response.getGeoId());
                    user.setAddress(response.getAddress());
                    user.setImageUrl(response.getAvatar());
                    user.setEmail(response.getEmail());
                    user.setPhone(response.getPhone());
                    user.setBio(response.getBio());
                    user.setTknl(response.getTknl());
                    getDataManager().setCurrentUser(user);

                    getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
                    getDataManager().setAccessToken(response.getAccessToken());
                    getDataManager().setAmout(response.getAmount());
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            setIsLoading(false);
                            getNavigator().openMainActivity();
                            ProcessBar.endProgress();
                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                            ProcessBar.endProgress();
                        }
                )
        );
    }

    public void checkLogin() {
        if (getDataManager().getCurrentUserLoggedInMode() != DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType() &&
                getDataManager().getAccessToken() != null) {
            getNavigator().openMainActivity();
            getDataManager().updateApiHeader(getDataManager().getAccessToken());
        }
    }
}
