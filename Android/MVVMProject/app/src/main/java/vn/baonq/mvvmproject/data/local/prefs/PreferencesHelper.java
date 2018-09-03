package vn.baonq.mvvmproject.data.local.prefs;

import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.model.db.UserDB;

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentLastName();

    void setCurrentLastname(String lastname);

    String getCurrentFirstname();

    void setCurrentFirstname(String firstname);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);
	
	String getCurrentUserPhone();

    void setCurrentUserPhone(String phone);

    String getCurrentUserAddress();

    void setCurrentUserAddress(String address);

    String getCurrentUserConfirmPass();

    void setCurrentUserConfirmPass(String confirmpass);

    String getCurrentUserBio();

    void setCurrentUserBio(String credit);

    String getBio();

    void setCurrentBio(String bio);

    void setCurrentUser(UserDB user);

    UserDB getCurrentUser();

    double getAmount();

    void setAmout(double amount);
}
