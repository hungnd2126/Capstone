package vn.baonq.mvvmproject.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

import javax.inject.Inject;

import vn.baonq.mvvmproject.BR;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.databinding.ActivityLoginBinding;
import vn.baonq.mvvmproject.ui.base.BaseActivity;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.utils.ProcessBar;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements
        LoginNavigator,
        LoginFacebook.LoginFacebookCallBack,
        LoginFacebook.GetProfileCallBack,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    public static final String TAG = LoginActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private static final int RC_SIGN_IN = 007;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    private CallbackManager mcCallbackManager;
    private LoginFacebook loginFacebook;

    @Inject
    LoginViewModel loginViewModel;

    private ActivityLoginBinding activityLoginBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return loginViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();
        loginViewModel.setNavigator(this);
        loginViewModel.checkLogin();

        initFbLogin();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


       mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void openMainActivity() {

        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSignInClick(){
        signOut();
        signIn();
    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void loginForm(String email, String password) {
        ProcessBar.runProgress(this);
        loginViewModel.loginForm(email, password);
    }

    @Override
    public void onFbClick() {
        ProcessBar.runProgress(this);
        activityLoginBinding.btnLoginFb.performClick();
    }

    //  Login fb code
    private void initFbLogin() {
        loginFacebook = new LoginFacebook(this);
        loginFacebook.init();
        mcCallbackManager = CallbackManager.Factory.create();
        loginFacebook.loginFacebook(mcCallbackManager, activityLoginBinding.btnLoginFb);
    }

    @Override
    public void onSuccessFb(LoginResult loginResult) {
        loginFacebook.getProfile(AccessToken.getCurrentAccessToken());
    }

    @Override
    public void onCancelFb() {
        Log.d(TAG, "onCancelFb");
    }

    @Override
    public void onErrorFb(FacebookException error) {
        Log.d(TAG, "OnErrorFb: " + error.toString());
    }

    @Override
    public void onCompleted(HashMap<String, String> profileUser) {
        loginViewModel.loginFb(profileUser);
    }

    // end of login fb code

    @Override
    public void login() {
        String email = activityLoginBinding.etEmail.getText().toString();
        String pwd = activityLoginBinding.etPassword.getText().toString();
        loginViewModel.login(email, pwd);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        mcCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn() {
        ProcessBar.runProgress(this);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Firebase Signout");
                    }
                });
    }



    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Firebase revoke access");
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acc = result.getSignInAccount();
            String url = "", email, id;

            if (acc.getPhotoUrl() != null) {
                url = acc.getPhotoUrl().toString();
            }
            email = acc.getEmail();
            id = acc.getId();

            Log.d("Name", acc.getDisplayName().toString());
            Log.d("Email", email);
            Log.d("Avatar", url);

        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getGivenName());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginViewModel.registerUserGG(acct);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
// [END auth_with_google]
}
