package vn.baonq.mvvmproject.ui.login;

public interface LoginNavigator {
    void login();
    void openMainActivity();
    void onSignInClick();
    void handleError(Throwable error);
    void loginForm(String email, String password);
    void onFbClick();
}
