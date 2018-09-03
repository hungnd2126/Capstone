package vn.baonq.mvvmproject.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vn.baonq.mvvmproject.ui.addpost.AddPostActivity;
import vn.baonq.mvvmproject.ui.addpost.AddPostModule;
import vn.baonq.mvvmproject.ui.addressDialog.AddressProvider;
import vn.baonq.mvvmproject.ui.addtrip.AddTripActivity;
import vn.baonq.mvvmproject.ui.addtrip.AddTripModule;
import vn.baonq.mvvmproject.ui.complete_offer.CompleteOfferActivity;
import vn.baonq.mvvmproject.ui.complete_offer.CompleteOfferModule;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivity;
import vn.baonq.mvvmproject.ui.delivery.DeliveryActivityModule;
import vn.baonq.mvvmproject.ui.login.LoginActivity;
import vn.baonq.mvvmproject.ui.login.LoginActivityModule;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.MainActivityModule;
import vn.baonq.mvvmproject.ui.main.Payment_Type.PaymentTypeActivity;
import vn.baonq.mvvmproject.ui.main.Payment_Type.PaymentTypeModule;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferActivity;
import vn.baonq.mvvmproject.ui.main.accept_offer.AcceptOfferModule;
import vn.baonq.mvvmproject.ui.main.delivery.DeliveryFragmentProvider;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivityModule;
import vn.baonq.mvvmproject.ui.main.home.HomeFragmentProvider;
import vn.baonq.mvvmproject.ui.main.home_buyer.HomeBuyerFragmentProvider;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferAcivity;
import vn.baonq.mvvmproject.ui.main.home_buyer_offer.BuyerOfferActivityModule;
import vn.baonq.mvvmproject.ui.main.home_main.HomeMainFragmentProvider;
import vn.baonq.mvvmproject.ui.main.make_offer.MakeOfferActivity;
import vn.baonq.mvvmproject.ui.main.make_offer.MakeOfferActivityModule;
import vn.baonq.mvvmproject.ui.main.message.dialogs.DialogFragmentProvider;
import vn.baonq.mvvmproject.ui.main.message.messages.CustomHolderMessagesActivity;
import vn.baonq.mvvmproject.ui.main.message.messages.HolderMessageModule;
import vn.baonq.mvvmproject.ui.main.message.video_call.IncomingCallScreenActivity;
import vn.baonq.mvvmproject.ui.main.message.video_call.VideoCallModule;
import vn.baonq.mvvmproject.ui.main.more.MoreProvider;
import vn.baonq.mvvmproject.ui.main.more.profile.ProfileActivity;
import vn.baonq.mvvmproject.ui.main.more.profile.ProfileActivityModule;
import vn.baonq.mvvmproject.ui.main.more.phone.PhoneActivity;
import vn.baonq.mvvmproject.ui.main.more.phone.PhoneActivityModule;
import vn.baonq.mvvmproject.ui.main.more.account.AccountActivity;
import vn.baonq.mvvmproject.ui.main.more.account.AccountActivityModule;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditActivity;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditActivityModule;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiActivity;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiActivityModule;
import vn.baonq.mvvmproject.ui.main.order.OrderFragmentProvider;
import vn.baonq.mvvmproject.ui.main.order.Received.ReceivedProvider;
import vn.baonq.mvvmproject.ui.main.order.Requested.RequestedProvider;
import vn.baonq.mvvmproject.ui.main.order.Transit.TransitProvider;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.CheckOrderActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.CheckOutActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentActivity;
import vn.baonq.mvvmproject.ui.main.payment.ui.activity.PaymentModule;
import vn.baonq.mvvmproject.ui.main.time_line.TimeLineActivity;
import vn.baonq.mvvmproject.ui.main.time_line.TimeLineModule;
import vn.baonq.mvvmproject.ui.map.MapActivity;
import vn.baonq.mvvmproject.ui.map.MapModule;
import vn.baonq.mvvmproject.ui.payment.PayPalActivity;
import vn.baonq.mvvmproject.ui.payment.PaypalModule;
import vn.baonq.mvvmproject.ui.rating.RatingProvider;
import vn.baonq.mvvmproject.ui.singleTripManage.STMActivity;
import vn.baonq.mvvmproject.ui.singleTripManage.STMActivityModule;
import vn.baonq.mvvmproject.ui.singleTripManage.STMReceived.STMReceivedProvider;
import vn.baonq.mvvmproject.ui.singleTripManage.STMTransit.STMTransitProvider;
import vn.baonq.mvvmproject.ui.singleTripManage.offering.OfferingProvider;
//import vn.baonq.mvvmproject.ui.singleTripManage.Suggested.SuggestedProvider;
import vn.baonq.mvvmproject.ui.singleTripManage.offered.OfferedProvider;
import vn.baonq.mvvmproject.ui.splash.SplashActivity;
import vn.baonq.mvvmproject.ui.splash.SplashActivityModule;
import vn.baonq.mvvmproject.ui.viewProfile.ViewProfileActivity;
import vn.baonq.mvvmproject.ui.viewProfile.ViewProfileModule;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeFragmentProvider.class,
            HomeMainFragmentProvider.class,
            HomeBuyerFragmentProvider.class,
            DeliveryFragmentProvider.class,
            OrderFragmentProvider.class,
            ReceivedProvider.class,
            RequestedProvider.class,
            TransitProvider.class,
            DialogFragmentProvider.class,
            MoreProvider.class
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = MapModule.class)
    abstract MapActivity mapActivity();
    @ContributesAndroidInjector(modules = VideoCallModule.class)
    abstract IncomingCallScreenActivity incomingCallScreenActivity();
    @ContributesAndroidInjector(modules = MakeOfferActivityModule.class)
    abstract MakeOfferActivity bindMakeOfferActivity();

    @ContributesAndroidInjector(modules = HolderMessageModule.class)
    abstract CustomHolderMessagesActivity customHolderMessagesActivity();

    @ContributesAndroidInjector(modules = {
            CompleteOfferModule.class,
            RatingProvider.class
    })
    abstract CompleteOfferActivity completeOfferActivity();

    @ContributesAndroidInjector(modules = PaymentTypeModule.class)
    abstract PaymentTypeActivity bindPaymentTypeActivity();

    @ContributesAndroidInjector(modules = DeliveryActivityModule.class)
    abstract DeliveryActivity bindDeliveryActivity();

    @ContributesAndroidInjector(modules = PaypalModule.class)
    abstract PayPalActivity bindPaypalActivity();

    @ContributesAndroidInjector(modules = {
            PaymentModule.class,
            RatingProvider.class
    })
    abstract CheckOrderActivity bindCheckOrderActivity();

    @ContributesAndroidInjector(modules = {
            PaymentModule.class,
    })
    abstract PaymentActivity bindPaymentActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginAcitivity();

    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract DetailActivity bindDetailActivity();

    @ContributesAndroidInjector(
            modules = AcceptOfferModule.class)
    abstract AcceptOfferActivity bindAcceptOfferActivity();

    @ContributesAndroidInjector(modules = AddTripModule.class)
    abstract AddTripActivity bindAddTripActivity();

    @ContributesAndroidInjector(modules = {
            //  BuyerOfferAcivity.class,
            BuyerOfferActivityModule.class,

    })
    abstract BuyerOfferAcivity bindBuyerOfferActivity();

    @ContributesAndroidInjector(modules = {
            STMActivityModule.class,
            RequestedProvider.class,
            OfferedProvider.class,
            STMReceivedProvider.class,
            STMTransitProvider.class,
            OfferingProvider.class
    })
    abstract STMActivity bindSTMActivity();

    @ContributesAndroidInjector(modules = {AddPostModule.class, AddressProvider.class})
    abstract AddPostActivity bindAddPostActivity();

    @ContributesAndroidInjector(modules = AccountActivityModule.class)
    abstract AccountActivity bindAccountActivity();

    @ContributesAndroidInjector(modules = CreditActivityModule.class)
    abstract CreditActivity bindCreditActivity();

    @ContributesAndroidInjector(modules = NotiActivityModule.class)
    abstract NotiActivity bindNotiActivity();

    @ContributesAndroidInjector(modules = {ProfileActivityModule.class, AddressProvider.class})
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector(modules = PhoneActivityModule.class)
    abstract PhoneActivity bindPhoneActivity();

    @ContributesAndroidInjector(modules = TimeLineModule.class)
    abstract TimeLineActivity bindTimeLineActivity();

    @ContributesAndroidInjector(modules = ViewProfileModule.class)
    abstract ViewProfileActivity bindViewProfileActivity();
}
