package vn.baonq.mvvmproject.utils;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Patterns;

import com.sinch.android.rtc.calling.CallState;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.baonq.mvvmproject.R;

import static vn.baonq.mvvmproject.utils.AppContansts.TIMESTAMP_FORMAT;


public final class CommonUtils {
    private CommonUtils() {
    }

    ;

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(AppContansts.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String loadCountryFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String formatPrice(double value) {
        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            return formatter.format(value) + " VNĐ";
        } catch (Exception e) {
            return "";
        }
    }

    public static String toDateFormat(String base, String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.US);
        Date date = null;
        try {
            date = format1.parse(base);
        } catch (ParseException e) {
            return "";
        }
        SimpleDateFormat format2 = new SimpleDateFormat(format, Locale.US);
        return format2.format(date);
    }

    public static String changeText(String text) {
        if (text.equals(CallState.INITIATING.toString())) {
            return "Đang kết nối với ";
        } else if (text.equals(CallState.PROGRESSING.toString())) {
            return "Đang xử lý";
        } else if (text.equals(CallState.ESTABLISHED.toString())) {
            return "Đã kết nối với ";
        } else if (text.equals(CallState.ENDED.toString())) {
            return "Kết thúc";
        } else if (text.equals(CallState.TRANSFERRING.toString())) {
            return "chuyển tiết";
        }
        return "";
    }
}
