package vn.baonq.mvvmproject.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import vn.baonq.mvvmproject.data.local.db.DbHelper;
import vn.baonq.mvvmproject.data.local.prefs.PreferencesHelper;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.CheckoutViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.CrawlViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.HistoryViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Notification;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.OfferViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.PostViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.ReviewViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TimelineViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.Rating;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.TripViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.UserProfileViewModel;
import vn.baonq.mvvmproject.data.model.api.ApiViewModel.WalletViewModel;
import vn.baonq.mvvmproject.data.model.api.DialogViewModel;
import vn.baonq.mvvmproject.data.model.api.reponse.BaseResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.CreateTripResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.GetListOfferOnPostResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.GetTripByUserResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.LoginResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.RequestedVMResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateInformationRespone;
import vn.baonq.mvvmproject.data.model.api.request.CreatePostRequest;
import vn.baonq.mvvmproject.data.model.api.request.CreateTripRequest;
import vn.baonq.mvvmproject.data.model.api.request.GetListOfferOnPostRequest;
import vn.baonq.mvvmproject.data.model.api.request.LoginRequest;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateInformationRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateAccountRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateAccountResponse;
import vn.baonq.mvvmproject.data.model.api.request.UpdatePhoneRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdatePhoneResponse;
import vn.baonq.mvvmproject.data.model.api.request.UpdateCreditRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateCreditResponse;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.data.remote.ApiHeader;
import vn.baonq.mvvmproject.data.remote.ApiHelper;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;

@Singleton
public class AppDataManager implements DataManager {
    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<LoginResponse> doLoginFormApiCall(LoginRequest.LoginFormRequest request) {
        return mApiHelper.doLoginFormApiCall(request);
    }

    @Override
    public Single<LoginResponse> doLoginFbApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doLoginFbApiCall(request);
    }

    @Override
    public Single<LoginResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public Single<List<PostViewModel>> getAllPost() {
        return mApiHelper.getAllPost();
    }

    @Override
    public Single<List<PostViewModel>> getPostByTrip(int TripId) {
        return mApiHelper.getPostByTrip(TripId);
    }

    @Override
    public Single<List<PostViewModel>> doGetOfferByTrip(int tripId, int type) {
        return mApiHelper.doGetOfferByTrip(tripId, type);
    }

    @Override
    public Single<UpdateInformationRespone> doUpdateInfomationApiCall(UpdateInformationRequest request) {
        return mApiHelper.doUpdateInfomationApiCall(request);
    }

    @Override
    public Single<UpdateAccountResponse> doUpdateAccountApiCall(UpdateAccountRequest request) {
        return mApiHelper.doUpdateAccountApiCall(request);
    }

    @Override
    public Single<List<DialogViewModel>> findUser(String Username) {
        return mApiHelper.findUser(Username);
    }

    @Override
    public Single<UpdatePhoneResponse> doUpdatePhoneApiCall(UpdatePhoneRequest request) {
        return mApiHelper.doUpdatePhoneApiCall(request);
    }

    @Override
    public Single<UpdateCreditResponse> doUpdateCreditApiCall(UpdateCreditRequest request) {
        return mApiHelper.doUpdateCreditApiCall(request);
    }

    @Override
    public Single<CreateTripResponse> doCreateTripApiCall(CreateTripRequest request) {
        return mApiHelper.doCreateTripApiCall(request);
    }

    @Override
    public Single<RequestedVMResponse> doGetPostByUser(RequestedVMRequest request) {
        return mApiHelper.doGetPostByUser(request);
    }

    @Override
    public Single<GetTripByUserResponse> doGetTripByUser(int postId) {
        return mApiHelper.doGetTripByUser(postId);
    }

    @Override
    public Single<GetListOfferOnPostResponse> doGetListOfferOnPost(GetListOfferOnPostRequest request) {
        return mApiHelper.doGetListOfferOnPost(request);
    }

    @Override
    public Single<OfferViewModel> makeOffer(OfferViewModel offerViewModel) {
        return mApiHelper.makeOffer(offerViewModel);
    }

    @Override
    public Single<OfferViewModel> updateOffer(OfferViewModel offerViewModel) {
        return mApiHelper.updateOffer(offerViewModel);
    }

    @Override
    public Single<BaseResponse> doCreatePost(PostViewModel request) {
        return mApiHelper.doCreatePost(request);
    }

    @Override
    public Single<BaseResponse> doUpdatePost(PostViewModel request) {
        return mApiHelper.doUpdatePost(request);
    }

    @Override
    public Single<CheckoutViewModel> doCheckOut(GetListOfferOnPostResponse.OfferResponse offerResponse) {
        return mApiHelper.doCheckOut(offerResponse);
    }

    @Override
    public Single<CheckoutViewModel> doCompletePost(PostViewModel vm) {
        return mApiHelper.doCompletePost(vm);
    }

    @Override
    public Single<GetListOfferOnPostResponse.OfferResponse> createOrder(GetListOfferOnPostResponse.OfferResponse offerResponse) {
        return mApiHelper.createOrder(offerResponse);
    }

    @Override
    public Single<PostViewModel> getPostDetail(int postId) {
        return mApiHelper.getPostDetail(postId);
    }

    @Override
    public Single<GetListOfferOnPostResponse.OfferResponse> doGetOfferOnPost(int OfferId) {
        return mApiHelper.doGetOfferOnPost(OfferId);
    }

    @Override
    public Single<List<TimelineViewModel>> getTimeline(int orderId) {
        return mApiHelper.getTimeline(orderId);
    }

    @Override
    public Single<String> uploadImage(String image) {
        return mApiHelper.uploadImage(image);
    }

    @Override
    public Single<String> doCreateRatingApi(Rating rating) {
        return mApiHelper.doCreateRatingApi(rating);
    }

    @Override
    public Single<CrawlViewModel> crawlData(String url) {
        return mApiHelper.crawlData(url);
    }

    @Override
    public Single<String> deleteOfferApi(int postId) {
        return mApiHelper.deleteOfferApi(postId);
    }

    @Override
    public Single<List<HistoryViewModel>> getHistory() {
        return mApiHelper.getHistory();
    }

    @Override
    public Single<List<Notification>> getNotification() {
        return mApiHelper.getNotification();
    }

    @Override
    public Single<UserProfileViewModel> getUserProfile(String userId) {
        return mApiHelper.getUserProfile(userId);
    }

    @Override
    public Single<List<ReviewViewModel>> getRating(String userId) {
        return mApiHelper.getRating(userId);
    }

    @Override
    public Single<DialogViewModel> getUser(String userId) {
        return mApiHelper.getUser(userId);
    }

    @Override
    public Single<String> getMoney() {
        return mApiHelper.getMoney();
    }

    @Override
    public Single<String> checkRating(int orderId) {
        return mApiHelper.checkRating(orderId);
    }

    @Override
    public Single<List<PostViewModel>> getPostByTrip(int ToCityId, int FromCityId) {
        return mApiHelper.getPostByTrip(ToCityId, FromCityId);
    }

    @Override
    public Single<List<PostViewModel>> getOfferOfBuyer(int tripId) {
        return mApiHelper.getOfferOfBuyer(tripId);

    }

    @Override
    public Single<String> createOfferOfBuyer(int postId, int tripId) {
        return mApiHelper.createOfferOfBuyer(postId, tripId);

    }

    @Override
    public Single<TimelineViewModel> updateTimeline(int orderId, int status, String description, double longitude, double latitude) {
        return mApiHelper.updateTimeline(orderId, status, description, longitude, latitude);
    }

    @Override
    public Single<String> deletePostApiCall(int id) {
        return mApiHelper.deletePostApiCall(id);
    }

    @Override
    public Single<List<TripViewModel>> getSuggestTrip() {
        return mApiHelper.getSuggestTrip();
    }

    @Override
    public Single<WalletViewModel> doNapTien(double value) {
        return mApiHelper.doNapTien(value);
    }

    @Override
    public Single<WalletViewModel> doRutTien(double value) {
        return mApiHelper.doRutTien(value);
    }

    @Override
    public void updateUserInfo(String accessToken,
                               String userId,
                               LoggedInMode loggedInMode,
                               String firstname,
                               String lastname,
                               String email,
                               String profilePicPath,
                               String phone,
                               String bio,
                               double amount) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentFirstname(firstname);
        setCurrentLastname(lastname);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
        setCurrentBio(bio);
        setCurrentUserPhone(phone);
        setAmout(amount);

        updateApiHeader(accessToken);
    }

    @Override
    public void updateUserAccount(String accessToken,
                                  String userId,
                                  LoggedInMode loggedInMode,
                                  String email,
                                  String newpass,
                                  String confirmpass) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserEmail(email);
        setCurrentUserConfirmPass(confirmpass);
    }

    @Override
    public void updateUserCredit(String accessToken,
                                 String userId,
                                 LoggedInMode loggedInMode,
                                 String credit) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
    }

    @Override
    public void updateUserPhone(String accessToken,
                                String userId,
                                LoggedInMode loggedInMode,
                                String phone) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserPhone(phone);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAuthorization(accessToken);
    }


    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentLastName() {
        return mPreferencesHelper.getCurrentLastName();
    }

    @Override
    public void setCurrentLastname(String lastname) {
        mPreferencesHelper.setCurrentLastname(lastname);
    }

    @Override
    public String getCurrentFirstname() {
        return mPreferencesHelper.getCurrentFirstname();
    }

    @Override
    public void setCurrentFirstname(String firstname) {
        mPreferencesHelper.setCurrentFirstname(firstname);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(BASE_URL + profilePicUrl);
    }

    @Override
    public String getCurrentUserBio() {
        return mPreferencesHelper.getCurrentUserBio();
    }

    @Override
    public void setCurrentUserBio(String newpass) {
        mPreferencesHelper.setCurrentUserBio(newpass);
    }

    @Override
    public String getCurrentUserConfirmPass() {
        return mPreferencesHelper.getCurrentUserConfirmPass();
    }

    @Override
    public void setCurrentUserConfirmPass(String confirmpass) {
        mPreferencesHelper.setCurrentUserConfirmPass(confirmpass);
    }

    @Override
    public String getCurrentUserAddress() {
        return mPreferencesHelper.getCurrentUserAddress();
    }

    @Override
    public void setCurrentUserAddress(String address) {
        mPreferencesHelper.setCurrentUserAddress(address);
    }

    @Override
    public String getBio() {
        return mPreferencesHelper.getBio();
    }

    @Override
    public void setCurrentBio(String bio) {
        mPreferencesHelper.setCurrentBio(bio);
    }

    @Override
    public void setCurrentUser(UserDB user) {
        mPreferencesHelper.setCurrentUser(user);
    }

    @Override
    public UserDB getCurrentUser() {
        return mPreferencesHelper.getCurrentUser();
    }

    @Override
    public double getAmount() {
        return mPreferencesHelper.getAmount();
    }

    @Override
    public void setAmout(double amount) {
        mPreferencesHelper.setAmout(amount);
    }

    @Override
    public String getCurrentUserPhone() {
        return mPreferencesHelper.getCurrentUserPhone();
    }

    @Override
    public void setCurrentUserPhone(String phone) {
        mPreferencesHelper.setCurrentUserPhone(phone);
    }

    @Override
    public void updateApiHeader(String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setAuthorization(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null,
                null,
                null,
                null,
                0
        );
    }


}
