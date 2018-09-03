package vn.baonq.mvvmproject.data;

import vn.baonq.mvvmproject.data.local.db.DbHelper;
import vn.baonq.mvvmproject.data.local.prefs.PreferencesHelper;
import vn.baonq.mvvmproject.data.remote.ApiHelper;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {
    void updateApiHeader(String accessToken);
    void setUserAsLoggedOut();

    void updateUserInfo(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String firstname,
            String lastname,
            String email,
            String profilePicPath,
            String phone,
            String bio,
            double amount);
	
	void updateUserAccount(
        String accessToken,
        String userId,
        LoggedInMode loggedInMode,
        String email,
        String newpass,
        String confirmpass
    );

    void updateUserPhone(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String phone
    );

    void updateUserCredit(
            String accessToken,
            String userId,
            LoggedInMode loggedInMode,
            String credit
    );

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
