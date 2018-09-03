package vn.baonq.mvvmproject.utils;

import android.app.ProgressDialog;
import android.content.Context;

import vn.baonq.mvvmproject.R;

public class ProcessBar {
    public static ProgressDialog progress;

    public static void runProgress(Context context) {
        progress = new ProgressDialog(context, R.style.Proccess);
        progress.setTitle("Đang xử lý");
        progress.setMessage("Vui lòng chờ hệ thống...");
        progress.setCancelable(false);
        progress.show();
    }

    public static void endProgress() {
        progress.dismiss();
    }
}
