package vn.baonq.mvvmproject.data.remote;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
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
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateAccountResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateCreditResponse;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateInformationRespone;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdatePhoneResponse;
import vn.baonq.mvvmproject.data.model.api.request.CreatePostRequest;
import vn.baonq.mvvmproject.data.model.api.request.CreateTripRequest;
import vn.baonq.mvvmproject.data.model.api.request.GetListOfferOnPostRequest;
import vn.baonq.mvvmproject.data.model.api.request.LoginRequest;
import vn.baonq.mvvmproject.data.model.api.request.RequestedVMRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateAccountRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateCreditRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdateInformationRequest;
import vn.baonq.mvvmproject.data.model.api.request.UpdatePhoneRequest;

import static vn.baonq.mvvmproject.data.remote.ApiUrl.CHECK_OUT;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CHECK_RATING_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.COMPLETE_POST;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CRAWL_DATA;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CREATE_OFFER_BY_BUYER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CREATE_ORDER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CREATE_POST_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CREATE_RATING_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.CREATE_TRIP_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.DELETE_OFFER_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.DELETE_POST_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.FIND_USER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GETALLPOST;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GETPOSTBYTRIP;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_ALL_TRIP_BY_USER_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_HISTORY_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_LIST_OFFER_BY_POST_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_MONEY_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_NOTIFICATION_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_OFFER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_OFFER_BUYER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_OFFER_BY_TRIP;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_POST_BY_TRIP;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_POST_BY_USER_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_POST_DETAIL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_RATING_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_SUGGEST_TRIP;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_TIMELINE;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_USER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.GET_USER_PROFILE_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.LOGINFB_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.LOGINFORM_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.MAKEOFFER;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.NAP_TIEN_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.RUT_TIEN_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.UPDATE_INFO_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.UPDATE_OFFER_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.UPDATE_POST_URL;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.UPDATE_TIMELINE;
import static vn.baonq.mvvmproject.data.remote.ApiUrl.UPLOAD_IMAGE;

public class AppApiHelper implements ApiHelper {
    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }


    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doLoginFormApiCall(LoginRequest.LoginFormRequest request) {
        return Rx2AndroidNetworking.post(LOGINFORM_URL)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doLoginFbApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.post(LOGINFB_URL)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }


    @Override
    public Single<LoginResponse> doLogoutApiCall() {
        return new Single<LoginResponse>() {
            @Override
            protected void subscribeActual(SingleObserver<? super LoginResponse> observer) {

            }
        };
    }

    @Override
    public Single<OfferViewModel> makeOffer(OfferViewModel offerViewModel) {
        return Rx2AndroidNetworking.post(MAKEOFFER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("PostId", offerViewModel.getPostId() + "")
                .addBodyParameter("TripId", offerViewModel.getTripId() + "")
                .addBodyParameter("DeliveryDate", offerViewModel.getDeliveryDate() + "")
                .addBodyParameter("ShippingFee", offerViewModel.getShippingFee() + "")
                .addBodyParameter("Message", offerViewModel.getMessage() + "")
                .build()
                .getObjectSingle(OfferViewModel.class);
    }

    @Override
    public Single<OfferViewModel> updateOffer(OfferViewModel offerViewModel) {
        return Rx2AndroidNetworking.post(UPDATE_OFFER_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("OfferId", offerViewModel.getId() + "")
                .addBodyParameter("DeliveryDate", offerViewModel.getDeliveryDate() + "")
                .addBodyParameter("ShippingFee", offerViewModel.getShippingFee() + "")
                .addBodyParameter("Message", offerViewModel.getMessage() + "")
                .build()
                .getObjectSingle(OfferViewModel.class);
    }

    @Override
    public Single<BaseResponse> doCreatePost(PostViewModel request) {
        try {
            return Rx2AndroidNetworking.post(CREATE_POST_URL)
                    .addHeaders(mApiHeader.getProtectedApiHeader())
                    .addJSONObjectBody(new JSONObject(request.toString()))
                    .build()
                    .getObjectSingle(BaseResponse.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Single<BaseResponse> doUpdatePost(PostViewModel request) {
        return Rx2AndroidNetworking.post(UPDATE_POST_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(BaseResponse.class);
    }

    @Override
    public Single<CheckoutViewModel> doCheckOut(GetListOfferOnPostResponse.OfferResponse offerResponse) {
        return Rx2AndroidNetworking.post(CHECK_OUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(offerResponse)
                .build()
                .getObjectSingle(CheckoutViewModel.class);
    }

    @Override
    public Single<CheckoutViewModel> doCompletePost(PostViewModel vm) {
        try {
            return Rx2AndroidNetworking.post(COMPLETE_POST)
                    .addHeaders(mApiHeader.getProtectedApiHeader())
                    .addJSONObjectBody(new JSONObject(vm.toString()))
                    .build()
                    .getObjectSingle(CheckoutViewModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Single<GetListOfferOnPostResponse.OfferResponse> createOrder(GetListOfferOnPostResponse.OfferResponse offerResponse) {
        return Rx2AndroidNetworking.post(CREATE_ORDER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(offerResponse)
                .build()
                .getObjectSingle(GetListOfferOnPostResponse.OfferResponse.class);
    }

    @Override
    public Single<PostViewModel> getPostDetail(int postId) {
        return Rx2AndroidNetworking.get(GET_POST_DETAIL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("postId", postId + "")
                .build()
                .getObjectSingle(PostViewModel.class);
    }

    @Override
    public Single<List<PostViewModel>> getAllPost() {
        return Rx2AndroidNetworking.post(GETALLPOST)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectListSingle(PostViewModel.class);
    }

    @Override
    public Single<List<PostViewModel>> getPostByTrip(int TripId) {
        return Rx2AndroidNetworking.post(GETPOSTBYTRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("TripId", TripId + "")
                .build()
                .getObjectListSingle(PostViewModel.class);
    }

    @Override
    public Single<List<PostViewModel>> doGetOfferByTrip(int tripId, int type) {
        return Rx2AndroidNetworking.get(GET_OFFER_BY_TRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("tripId", tripId + "")
                .addQueryParameter("type", type + "")
                .build()
                .getObjectListSingle(PostViewModel.class);
    }

    @Override
    public Single<UpdateInformationRespone> doUpdateInfomationApiCall(UpdateInformationRequest request) {
        return Rx2AndroidNetworking.post(UPDATE_INFO_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(UpdateInformationRespone.class);
    }

    @Override
    public Single<UpdateAccountResponse> doUpdateAccountApiCall(UpdateAccountRequest request) {
        return Rx2AndroidNetworking.post(UPDATE_INFO_URL)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(UpdateAccountResponse.class);
    }

    @Override
    public Single<UpdatePhoneResponse> doUpdatePhoneApiCall(UpdatePhoneRequest request) {
        return Rx2AndroidNetworking.post(UPDATE_INFO_URL)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(UpdatePhoneResponse.class);
    }


    @Override
    public Single<UpdateCreditResponse> doUpdateCreditApiCall(UpdateCreditRequest request) {
        return Rx2AndroidNetworking.post(UPDATE_INFO_URL)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(UpdateCreditResponse.class);
    }

    @Override
    public Single<CreateTripResponse> doCreateTripApiCall(CreateTripRequest request) {
        return Rx2AndroidNetworking.post(CREATE_TRIP_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(CreateTripResponse.class);
    }

    @Override
    public Single<RequestedVMResponse> doGetPostByUser(RequestedVMRequest request) {
        return Rx2AndroidNetworking.post(GET_POST_BY_USER_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(RequestedVMResponse.class);
    }

    @Override
    public Single<GetTripByUserResponse> doGetTripByUser(int postId) {
        return Rx2AndroidNetworking.post(GET_ALL_TRIP_BY_USER_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("Id", postId+"")
                .build()
                .getObjectSingle(GetTripByUserResponse.class);
    }

    @Override
    public Single<GetListOfferOnPostResponse> doGetListOfferOnPost(GetListOfferOnPostRequest request) {
        return Rx2AndroidNetworking.get(GET_LIST_OFFER_BY_POST_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id", request.getPostId() + "")
                .build()
                .getObjectSingle(GetListOfferOnPostResponse.class);
    }

    @Override
    public Single<GetListOfferOnPostResponse.OfferResponse> doGetOfferOnPost(int OfferId) {
        return Rx2AndroidNetworking.get(GET_OFFER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id", OfferId + "")
                .build()
                .getObjectSingle(GetListOfferOnPostResponse.OfferResponse.class);
    }

    @Override
    public Single<List<DialogViewModel>> findUser(String Username) {
        return Rx2AndroidNetworking.post(FIND_USER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("FirstName", Username)
                .build()
                .getObjectListSingle(DialogViewModel.class);
    }

    @Override
    public Single<String> uploadImage(String image) {
        return Rx2AndroidNetworking.post(UPLOAD_IMAGE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("Path", image)
                .build()
                .getStringSingle();
    }

    @Override
    public Single<List<TimelineViewModel>> getTimeline(int orderId) {
        return Rx2AndroidNetworking.post(GET_TIMELINE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("OrderId", orderId + "")
                .build()
                .getObjectListSingle(TimelineViewModel.class);
    }


    @Override
    public Single<String> doCreateRatingApi(Rating rating) {
        return Rx2AndroidNetworking.post(CREATE_RATING_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(rating)
                .build()
                .getStringSingle();
    }

    @Override
    public Single<TimelineViewModel> updateTimeline(int orderId, int status, String description, double longitude, double latitude) {
        return Rx2AndroidNetworking.post(UPDATE_TIMELINE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("OrderId", orderId + "")
                .addBodyParameter("Status", status + "")
                .addBodyParameter("Description", description)
                .addBodyParameter("Longitude", longitude + "")
                .addBodyParameter("Latitude", latitude + "")
                .build()
                .getObjectSingle(TimelineViewModel.class);
    }

    @Override
    public Single<String> deletePostApiCall(int id) {
        return Rx2AndroidNetworking.get(DELETE_POST_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("id", id + "")
                .build()
                .getStringSingle();
    }

    @Override
    public Single<List<TripViewModel>> getSuggestTrip() {
        return Rx2AndroidNetworking.post(GET_SUGGEST_TRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectListSingle(TripViewModel.class);
    }

    @Override
    public Single<List<PostViewModel>> getPostByTrip(int ToCityId, int FromCityId) {
        return Rx2AndroidNetworking.post(GET_POST_BY_TRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("ToCityId",String.valueOf(ToCityId))
                .addBodyParameter("FromCityId",String.valueOf(FromCityId))
                .build()
                .getObjectListSingle(PostViewModel.class);
    }

    @Override
    public Single<List<PostViewModel>> getOfferOfBuyer(int tripId) {
        return Rx2AndroidNetworking.post(GET_OFFER_BUYER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("Id",String.valueOf(tripId))
                .build()
                .getObjectListSingle(PostViewModel.class);
    }

    @Override
    public Single<String> createOfferOfBuyer(int postId, int tripId) {
        return Rx2AndroidNetworking.post(CREATE_OFFER_BY_BUYER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("postId",String.valueOf(postId))
                .addQueryParameter("tripId", String.valueOf(tripId))
                .build()
                .getStringSingle();
    }

    @Override
    public Single<WalletViewModel> doNapTien(double value) {
        return Rx2AndroidNetworking.post(NAP_TIEN_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("Value", value + "")
                .build()
                .getObjectSingle(WalletViewModel.class);
    }

    @Override
    public Single<WalletViewModel> doRutTien(double value) {
        return Rx2AndroidNetworking.post(RUT_TIEN_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("Value", value + "")
                .build()
                .getObjectSingle(WalletViewModel.class);
    }

    @Override
    public Single<CrawlViewModel> crawlData(String url) {
        return Rx2AndroidNetworking.post(CRAWL_DATA)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("url", url)
                .build()
                .getObjectSingle(CrawlViewModel.class);
    }

    @Override
    public Single<String> deleteOfferApi(int postId) {
        return Rx2AndroidNetworking.post(DELETE_OFFER_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("postId",String.valueOf(postId))
                .build()
                .getStringSingle();
    }

    @Override
    public Single<List<HistoryViewModel>> getHistory() {
        return Rx2AndroidNetworking.get(GET_HISTORY_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectListSingle(HistoryViewModel.class);
    }

    @Override
    public Single<List<Notification>> getNotification() {
        return Rx2AndroidNetworking.get(GET_NOTIFICATION_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectListSingle(Notification.class);
    }

    @Override
    public Single<UserProfileViewModel> getUserProfile(String userId) {
        return Rx2AndroidNetworking.get(GET_USER_PROFILE_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("userId", userId)
                .build()
                .getObjectSingle(UserProfileViewModel.class);
    }

    @Override
    public Single<List<ReviewViewModel>> getRating(String userId) {
        return Rx2AndroidNetworking.get(GET_RATING_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("userId", userId)
                .build()
                .getObjectListSingle(ReviewViewModel.class);
    }

    @Override
    public Single<DialogViewModel> getUser(String userId) {
        return Rx2AndroidNetworking.post(GET_USER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("UserId", userId)
                .build()
                .getObjectSingle(DialogViewModel.class);
    }

    @Override
    public Single<String> getMoney() {
        return Rx2AndroidNetworking.get(GET_MONEY_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getStringSingle();
    }

    @Override
    public Single<String> checkRating(int orderId) {
        return Rx2AndroidNetworking.get(CHECK_RATING_URL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("orderId", String.valueOf(orderId))
                .build()
                .getStringSingle();
    }
}
