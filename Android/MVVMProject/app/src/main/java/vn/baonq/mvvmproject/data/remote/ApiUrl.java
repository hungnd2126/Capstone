package vn.baonq.mvvmproject.data.remote;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_CHECK_RATING;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_CREATE_POST;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_CREATE_RATING;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_CREATE_TRIP;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_DELETE_OFFER;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_DELETE_POST;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_HISTORY;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_MONEY;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_NOTIFICATION;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_OFFER_BY_POST;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_OFFER_BY_TRIP;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_POST_BY_USER;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_POST_DETAIL;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_RATING;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_TRIP_BY_USER;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_GET_USER_PROFILE;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_LOGINFB;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_LOGINFORM;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_NAP_TIEN;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_RUT_TIEN;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_UPDATE_INFO;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_UPDATE_OFFER;
import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.ENDPOINT_UPDATE_POST;

public final class ApiUrl {

    public ApiUrl() {

    }

    public static final String LOGINFORM_URL = BASE_URL + ENDPOINT_LOGINFORM;
    public static final String LOGINFB_URL = BASE_URL + ENDPOINT_LOGINFB;
    public static final String GETALLPOST = BASE_URL + "/api/post/getallpost";
    public static final String MAKEOFFER = BASE_URL + "/api/offer/makeoffer";
    public static final String GETPOSTBYTRIP = BASE_URL + "/api/post/get_post_offed_trip";
    public static final String UPDATE_INFO_URL = BASE_URL + ENDPOINT_UPDATE_INFO;
    public static final String GET_POST_BY_USER_URL = BASE_URL + ENDPOINT_GET_POST_BY_USER;
    public static final String CREATE_TRIP_URL = BASE_URL + ENDPOINT_CREATE_TRIP;
    public static final String GET_ALL_TRIP_BY_USER_URL = BASE_URL + ENDPOINT_GET_TRIP_BY_USER;
    public static final String GET_LIST_OFFER_BY_POST_URL = BASE_URL + ENDPOINT_GET_OFFER_BY_POST;
    public static final String CREATE_POST_URL = BASE_URL + ENDPOINT_CREATE_POST;
    public static final String CHECK_OUT = BASE_URL + "/api/order/checkouts";
    public static final String CREATE_ORDER = BASE_URL + "/api/order/CreateOrder";
    public static final String GET_OFFER = BASE_URL + "/api/post/get-offer";
    public static final String COMPLETE_POST = BASE_URL + "/api/order/complete-order";
    public static final String FIND_USER = BASE_URL + "/api/user/findUser";
    public static final String GET_POST_DETAIL = BASE_URL + ENDPOINT_GET_POST_DETAIL;
    public static final String GET_OFFER_BY_TRIP = BASE_URL + ENDPOINT_GET_OFFER_BY_TRIP;
    public static final String UPLOAD_IMAGE = BASE_URL + "/api/image/upload-image";
    public static final String GET_TIMELINE = BASE_URL + "/api/tracking-order/get-time-line";
    public static final String CREATE_RATING_URL = BASE_URL + ENDPOINT_CREATE_RATING;
    public static final String UPDATE_TIMELINE = BASE_URL + "/api/tracking-order/update-time-line";
    public static final String DELETE_POST_URL = BASE_URL + ENDPOINT_DELETE_POST;
    public static final String GET_SUGGEST_TRIP = BASE_URL + "/api/trip/get-suggest-trip";
    public static final String GET_POST_BY_TRIP = BASE_URL + "/api/post/get-post-by-trip";
    public static final String GET_OFFER_BUYER = BASE_URL + "/api/offer/get-offer-of-buyer";
    public static final String CREATE_OFFER_BY_BUYER = BASE_URL + "/api/offer/buyer-make-offer";
    public static final String UPDATE_POST_URL = BASE_URL + ENDPOINT_UPDATE_POST;
    public static final String NAP_TIEN_URL = BASE_URL + ENDPOINT_NAP_TIEN;
    public static final String RUT_TIEN_URL = BASE_URL + ENDPOINT_RUT_TIEN;
    public static final String DELETE_OFFER_URL = BASE_URL + ENDPOINT_DELETE_OFFER;
    public static final String CRAWL_DATA = BASE_URL + "/api/post/get-info-by-link";
    public static final String UPDATE_OFFER_URL = BASE_URL + ENDPOINT_UPDATE_OFFER;
    public static final String GET_HISTORY_URL = BASE_URL + ENDPOINT_GET_HISTORY;
    public static final String GET_NOTIFICATION_URL = BASE_URL + ENDPOINT_GET_NOTIFICATION;
    public static final String GET_USER_PROFILE_URL = BASE_URL + ENDPOINT_GET_USER_PROFILE;
    public static final String GET_RATING_URL = BASE_URL + ENDPOINT_GET_RATING;
    public static final String GET_MONEY_URL = BASE_URL + ENDPOINT_GET_MONEY;
    public static final String CHECK_RATING_URL = BASE_URL + ENDPOINT_CHECK_RATING;
    public static final String GET_USER = BASE_URL + "/api/user/get-user";



}
