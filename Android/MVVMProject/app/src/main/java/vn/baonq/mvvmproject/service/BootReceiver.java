package vn.baonq.mvvmproject.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import vn.baonq.mvvmproject.ui.main.MainActivity;

public class BootReceiver extends BroadcastReceiver {
    Intent intent1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Boot Receiver", "restart service: " + getResultCode() );
        intent1 = new Intent(context, SignalRService.class);
        context.startService(intent1);
    }
}
