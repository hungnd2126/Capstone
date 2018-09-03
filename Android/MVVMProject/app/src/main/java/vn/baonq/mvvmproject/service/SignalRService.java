package vn.baonq.mvvmproject.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.AppDataManager;
import vn.baonq.mvvmproject.data.local.prefs.AppPreferencesHelper;
import vn.baonq.mvvmproject.data.model.db.UserDB;
import vn.baonq.mvvmproject.ui.main.MainActivity;
import vn.baonq.mvvmproject.ui.main.detail_order.DetailActivity;
import vn.baonq.mvvmproject.ui.main.more.credit.CreditActivity;
import vn.baonq.mvvmproject.ui.main.more.noti.NotiActivity;
import vn.baonq.mvvmproject.utils.CommonUtils;

import static vn.baonq.mvvmproject.data.remote.ApiEndPoint.BASE_URL;
import static vn.baonq.mvvmproject.utils.AppContansts.PREF_NAME;

public class SignalRService extends Service {
    public static final String TAG = SignalRService.class.getSimpleName();
    private HubConnection mHubConnection;
    private HubProxy mHubProxy;

    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    private NotificationListener serviceCallBacks;

    public class LocalBinder extends Binder {
        public SignalRService getService() {
            return SignalRService.this;
        }
    }

    public void setCallbacks(NotificationListener callbacks) {
        serviceCallBacks = callbacks;
    }

    //<editor-fold desc ="override method">
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Create");
        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "on Start Command");
        return START_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "on Destroy");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "Task removed");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        startSignalR();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "Unbind");
        return super.onUnbind(intent);
    }
    // </editor-fold>

    private void startSignalR() {
        Log.i(TAG, "Start Signal R");
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        UserDB user = new UserDB().fromJson(preferences.getString("PREF_KEY_USER", null));
        String username = user != null ? user.getEmail() : "o";
        Credentials credentials = request -> request.addHeader("username", username);
        String serverUrl = BASE_URL;
        mHubConnection = new HubConnection(serverUrl);
        mHubConnection.setCredentials(credentials);
        String NOTIFICATION_HUB = "NotificationHub";
        mHubProxy = mHubConnection.createHubProxy(NOTIFICATION_HUB);
        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);

        try {
            signalRFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
        String COMPLETE_ORDER = "completeOrder";
        mHubProxy.on(COMPLETE_ORDER,
                (msg, orderId) -> {
                    // display Toast message
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            myIntent.putExtra("callFrom", "notification");
                            myIntent.putExtra("postId", Integer.valueOf(orderId));
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            sendNotification(msg, "Đơn hàng hoàn tất", pendingIntent);
                        }
                    });
                }
                , String.class, String.class);

        String RECEIVED_OFFER = "receivedOffer";
        mHubProxy.on(RECEIVED_OFFER,
                (msg, postId) -> {
                    mHandler.post(() -> {
                        Intent myIntent = new Intent(getApplicationContext(), DetailActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        myIntent.putExtra("callFrom", "notification");
                        myIntent.putExtra("postId", Integer.valueOf(postId));
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        sendNotification(msg, "Nhận được đề nghị mới", pendingIntent);
                    });
                }
                , String.class, String.class);

        String ADMIN_COMPLETE_ORDER = "admincompleteorder";
        mHubProxy.on(ADMIN_COMPLETE_ORDER,
                (msg, postId) -> {
                    mHandler.post(() -> {
                        Intent myIntent = CreditActivity.newIntent(getApplicationContext());
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        myIntent.putExtra("callFrom", "notification");
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        sendNotification("Bạn nhận được " + CommonUtils.formatPrice(Long.parseLong(msg)) + " từ đơn hàng hoàn tất", "Hệ thống xác nhận đơn hàng hoàn tất", pendingIntent);
                    });
                }
                , String.class, String.class);

        String ACCEPT_OFFER = "acceptOffer";
        mHubProxy.on(ACCEPT_OFFER,
                (msg, postId) -> {
                    // display Toast message
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent myIntent = new Intent(getApplicationContext(), NotiActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            myIntent.putExtra("callFrom", "notification");
                            myIntent.putExtra("postId", Integer.valueOf(postId));
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            sendNotification(msg, "Đề nghị được nhận", pendingIntent);
                        }
                    });
                }
                , String.class, String.class);

        String NEW_TIMELINE = "newTimeline";
        mHubProxy.on(NEW_TIMELINE,
                (msg, postId) -> {
                    // display Toast message
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent myIntent = new Intent(getApplicationContext(), NotiActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            myIntent.putExtra("postId", Integer.valueOf(postId));
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            sendNotification(msg, "Cập nhật đơn hàng ", pendingIntent);
                        }
                    });
                }
                , String.class, String.class);

    }

    private void sendNotification(String messageBody, String messageTitle, PendingIntent pendingIntent) {
        Context context = getApplicationContext();
        Notification.Builder builder;
        builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}

