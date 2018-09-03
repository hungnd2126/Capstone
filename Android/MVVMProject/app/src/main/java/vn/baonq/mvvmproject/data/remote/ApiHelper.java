package vn.baonq.mvvmproject.data.remote;

import java.util.List;

import io.reactivex.Single;
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
import vn.baonq.mvvmproject.data.model.api.request.UpdateCreditRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateCreditResponse;
import vn.baonq.mvvmproject.data.model.api.request.UpdatePhoneRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdatePhoneResponse;
import vn.baonq.mvvmproject.data.model.api.request.UpdateAccountRequest;
import vn.baonq.mvvmproject.data.model.api.reponse.UpdateAccountResponse;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doLoginFormApiCall(LoginRequest.LoginFormRequest request);

    Single<LoginResponse> doLoginFbApiCall(LoginRequest.ServerLoginRequest request);

    Single<LoginResponse> doLogoutApiCall();

    Single<List<PostViewModel>> getAllPost();

    Single<List<PostViewModel>> getPostByTrip(int TripId);

    Single<List<PostViewModel>> doGetOfferByTrip(int tripId, int type);

    Single<UpdateInformationRespone> doUpdateInfomationApiCall(UpdateInformationRequest request);

    Single<UpdateAccountResponse> doUpdateAccountApiCall(UpdateAccountRequest request);

    Single<UpdateCreditResponse> doUpdateCreditApiCall(UpdateCreditRequest request);

    Single<UpdatePhoneResponse> doUpdatePhoneApiCall(UpdatePhoneRequest request);

    Single<CreateTripResponse> doCreateTripApiCall(CreateTripRequest request);

    Single<RequestedVMResponse> doGetPostByUser(RequestedVMRequest request);

    Single<GetTripByUserResponse> doGetTripByUser(int postId);

    Single<GetListOfferOnPostResponse> doGetListOfferOnPost(GetListOfferOnPostRequest request);

    Single<GetListOfferOnPostResponse.OfferResponse> doGetOfferOnPost(int OfferId);

    Single<OfferViewModel> makeOffer(OfferViewModel offerViewModel);

    Single<OfferViewModel> updateOffer(OfferViewModel offerViewModel);

    Single<BaseResponse> doCreatePost(PostViewModel request);

    Single<BaseResponse> doUpdatePost(PostViewModel request);

    Single<CheckoutViewModel> doCheckOut(GetListOfferOnPostResponse.OfferResponse offerResponse);

    Single<CheckoutViewModel> doCompletePost(PostViewModel vm);

    Single<GetListOfferOnPostResponse.OfferResponse> createOrder(GetListOfferOnPostResponse.OfferResponse offerResponse);

    Single<PostViewModel> getPostDetail(int postId);

    Single<List<DialogViewModel>> findUser(String Username);

    Single<String> uploadImage(String image);

    Single<List<TimelineViewModel>> getTimeline(int orderId);

    Single<String> doCreateRatingApi(Rating rating);

    Single<TimelineViewModel> updateTimeline(int orderId, int status, String description, double longitude, double latitude);

    Single<String> deletePostApiCall(int id);

    Single<List<TripViewModel>> getSuggestTrip();

    Single<List<PostViewModel>> getPostByTrip(int ToCityId, int FromCityId);

    Single<List<PostViewModel>> getOfferOfBuyer(int tripId);

    Single<String> createOfferOfBuyer(int postId, int tripId);

    Single<WalletViewModel> doNapTien(double value);

    Single<WalletViewModel> doRutTien(double value);

    Single<CrawlViewModel> crawlData(String url);

    Single<String> deleteOfferApi(int postId);

    Single<List<HistoryViewModel>> getHistory();

    Single<List<Notification>> getNotification();

    Single<UserProfileViewModel> getUserProfile(String userId);

    Single<List<ReviewViewModel>> getRating(String userId);

    Single<DialogViewModel> getUser(String userId);

    Single<String> getMoney();

    Single<String> checkRating(int orderId);

}

