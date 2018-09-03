package vn.baonq.mvvmproject.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginFacebook {

    public LoginFacebook(Context context) {
        mContext = context;
    }

    public static final String TAG = LoginFacebook.class.getSimpleName();

    public static Context mContext;

    public static final List<String> PERMISSTION = new ArrayList<>();

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FRIST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String AVATARFB = "avatar";
    public static final String ACCESSTOKEN = "token";


    public interface LoginFacebookCallBack {
        void onSuccessFb(final LoginResult loginResult);

        void onCancelFb();

        void onErrorFb(FacebookException error);
    }

    public interface GetProfileCallBack {
        void onCompleted(HashMap<String, String> profileUser);
    }

    public void init() {
        FacebookSdk.sdkInitialize(mContext);
        AppEventsLogger.activateApp(mContext);
    }

    public void loginFacebook(CallbackManager callbackManager, LoginButton loginButton) {

        PERMISSTION.add("public_profile");
        loginButton.setReadPermissions(PERMISSTION);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d("LoginFb", "onSuccess: login fb callback");
                LoginFacebookCallBack loginFacebookListener = (LoginFacebookCallBack) mContext;
                loginFacebookListener.onSuccessFb(loginResult);
            }

            @Override
            public void onCancel() {
                Log.d("LoginFb", "onCancel: ");
                LoginFacebookCallBack loginFacebookListener = (LoginFacebookCallBack) mContext;
                loginFacebookListener.onCancelFb();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LoginFb", "onError: ");
                LoginFacebookCallBack loginFacebookListener = (LoginFacebookCallBack) mContext;
                loginFacebookListener.onErrorFb(error);
            }
        });

//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

    }

    public void getProfile(AccessToken accessToken) {
        final HashMap<String, String> profileUser = new HashMap<>();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    try {
                        String id = object.getString(ID);
                        if (!object.isNull(ID)) {
                            profileUser.put(ID, id);
                        }
                        profileUser.put(NAME, object.getString(NAME));
                        profileUser.put(FRIST_NAME, object.getString(FRIST_NAME));
                        profileUser.put(LAST_NAME, object.getString(LAST_NAME));
                        if (!object.isNull(EMAIL)) {
                            profileUser.put(EMAIL, object.getString(EMAIL));
                        }
                        profileUser.put(AVATARFB, "https://graph.facebook.com/" + id + "/picture?type=large");
                        profileUser.put(ACCESSTOKEN, AccessToken.getCurrentAccessToken().getToken());
                        GetProfileCallBack getProfileCallBack = (GetProfileCallBack) mContext;
                        getProfileCallBack.onCompleted(profileUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static void logoutFb() {
        if (AccessToken.getCurrentAccessToken() != null)
            LoginManager.getInstance().logOut();
    }
}
