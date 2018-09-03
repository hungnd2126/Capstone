package vn.baonq.mvvmproject.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.di.PreferenceInfo;

public class AppPreferencesHelper implements PreferencesHelper {
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_LAST_NAME = "PREF_KEY_CURRENT_LAST_NAME";

    private static final String PREF_KEY_CURRENT_FIRST_NAME = "PREF_KEY_CURRENT_FIRST_NAME";

    private static final String PREF_KEY_CURRENT_USER_BIO = "PREF_KEY_CURRENT_USER_BIO";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    private static final String PREF_KEY_CURRENT_USER_PHONE = "PREF_KEY_CURRENT_USER_PHONE";

    private static final String PREF_KEY_CURRENT_USER_NEW_PASS = "PREF_KEY_CURRENT_USER_NEW_PASS";

    private static final String PREF_KEY_CURRENT_USER_ADDRESS = "PREF_KEY_CURRENT_USER_ADDRESS";

    private static final String PREF_KEY_CURRENT_USER_AMOUNT = "PREF_KEY_CURRENT_USER_AMOUNT";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_USER = "PREF_KEY_USER";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserId() {
        String userId = mPrefs.getString(PREF_KEY_CURRENT_USER_ID, "-1");
        return userId == "-1" ? null : userId;
    }


    @Override
    public void setCurrentUserId(String userId) {
        String id = userId == null ? "-1" : userId;
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentLastName() {
        return mPrefs.getString(PREF_KEY_CURRENT_LAST_NAME, null);
    }

    @Override
    public void setCurrentLastname(String lastname) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_LAST_NAME, lastname).apply();
    }

    @Override
    public String getCurrentFirstname() {
        return mPrefs.getString(PREF_KEY_CURRENT_FIRST_NAME, null);
    }

    @Override
    public void setCurrentFirstname(String firstname) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_FIRST_NAME, firstname).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public String getCurrentUserPhone() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PHONE, null);
    }

    @Override
    public void setCurrentUserPhone(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PHONE, phone).apply();
    }

    @Override
    public String getCurrentUserBio() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_BIO, null);
    }

    @Override
    public void setCurrentUserBio(String bio) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_BIO, bio).apply();
    }

    @Override
    public String getBio() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_BIO, null);
    }

    @Override
    public void setCurrentBio(String bio) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_BIO, bio).apply();
    }

    @Override
    public void setCurrentUser(UserDB user) {
        mPrefs.edit().putString(PREF_KEY_USER, user.toJson()).apply();
    }

    @Override
    public UserDB getCurrentUser() {
        return new UserDB().fromJson(mPrefs.getString(PREF_KEY_USER, null));
    }

    @Override
    public double getAmount() {
        return mPrefs.getFloat(PREF_KEY_CURRENT_USER_AMOUNT, 0);
    }

    @Override
    public void setAmout(double amount) {
        mPrefs.edit().putFloat(PREF_KEY_CURRENT_USER_AMOUNT, (float) amount).apply();
    }

    @Override
    public String getCurrentUserAddress() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ADDRESS, null);
    }

    @Override
    public void setCurrentUserAddress(String newpass) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ADDRESS, newpass).apply();
    }

    @Override
    public String getCurrentUserConfirmPass() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NEW_PASS, null);
    }

    @Override
    public void setCurrentUserConfirmPass(String confirmpass) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NEW_PASS, confirmpass).apply();
    }
}
