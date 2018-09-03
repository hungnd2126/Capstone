package vn.baonq.mvvmproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class NotificationService extends Service {
    private Timer mTimer;
    private TimerTask timerTask;
    private boolean isPending;
    private NotificationListener serviceCallBacks;
    private int count = 300;
    public void setCallbacks(NotificationListener callbacks) {
        serviceCallBacks = callbacks;
    }

    private final IBinder binder = new LocalBinder();

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public class LocalBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(serviceCallBacks!=null)
                    serviceCallBacks.setCount(count--);
            }
        };
        mTimer.schedule(timerTask, 1000, 1000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mTimer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        try {
            mTimer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("YouWillNeverKillMe");
        sendBroadcast(intent);
    }
}
