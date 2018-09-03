package vn.baonq.mvvmproject.data.remote;

public final class ApiEndPoint {

    private ApiEndPoint() {
    }

   // public static final String BASE_URL = "http://ef1307bf.ngrok.io";
//   public static final String BASE_URL = "http://172.16.1.192:8001";
    public static final String BASE_URL = "http://192.168.21.100:8001";

    public static final String ENDPOINT_LOGINFORM = "/api/user/loginform";
    public static final String ENDPOINT_LOGINFB = "/api/user/loginfb";
    public static final String ENDPOINT_UPDATE_INFO = "/api/user/update-info";
    public static final String ENDPOINT_UPDATE_POST = "/api/post/update-post";
    public static final String ENDPOINT_GET_POST_BY_USER = "/api/post/get-post-by-user";
    public static final String ENDPOINT_CREATE_TRIP = "/api/trip/create-trip";
    public static final String ENDPOINT_CREATE_POST = "/api/post/create-post";
    public static final String ENDPOINT_GET_TRIP_BY_USER = "/api/trip/get-all-trip-by-user";
    public static final String ENDPOINT_GET_OFFER_BY_POST = "/api/offer/get-list-offer-by-post";
    public static final String ENDPOINT_GET_POST_DETAIL = "/api/post/get-post-detail";
    public static final String ENDPOINT_GET_OFFER_BY_TRIP = "/api/offer/get-offer-by-trip";
    public static final String ENDPOINT_CREATE_RATING = "/api/rating/create-rating";
    public static final String ENDPOINT_DELETE_POST = "/api/post/delete-post";
    public static final String ENDPOINT_NAP_TIEN = "/api/user/nap-tien";
    public static final String ENDPOINT_RUT_TIEN = "/api/user/rut-tien";
    public static final String ENDPOINT_DELETE_OFFER = "/api/offer/delete-offer";
    public static final String ENDPOINT_UPDATE_OFFER = "/api/offer/update-offer";
    public static final String ENDPOINT_GET_HISTORY = "/api/transaction/get-history";
    public static final String ENDPOINT_GET_NOTIFICATION = "/api/notification/get-notification";
    public static final String ENDPOINT_GET_USER_PROFILE = "/api/rating/get-user-profile";
    public static final String ENDPOINT_GET_RATING = "/api/rating/get-rating";
    public static final String ENDPOINT_GET_MONEY = "/api/user/get-money";
    public static final String ENDPOINT_CHECK_RATING = "/api/rating/check-rating";


}