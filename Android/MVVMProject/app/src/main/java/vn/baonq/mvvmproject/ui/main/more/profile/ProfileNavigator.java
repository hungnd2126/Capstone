package vn.baonq.mvvmproject.ui.main.more.profile;

public interface ProfileNavigator {
    void saveProfile();
    void onUploadImage();
    void updateCompleted(String mess);
    void showAddressDialog();
}
