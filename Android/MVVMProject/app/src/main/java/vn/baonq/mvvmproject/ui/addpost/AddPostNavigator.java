package vn.baonq.mvvmproject.ui.addpost;

public interface AddPostNavigator {
    void onClickDateTimePicker();
    void showPart(int part, int action);
    void finishActivity(boolean isPaid);
    void onUploadImage();
    void updateCompleted(String mess);
    void createPost();
    void onClickPlace(int i);
    void runProgress();
    void endProgress();
}
