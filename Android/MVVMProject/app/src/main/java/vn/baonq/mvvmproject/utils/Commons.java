package vn.baonq.mvvmproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.security.MessageDigest;

import vn.baonq.mvvmproject.R;

public class Commons {

    public static String md5(String strMd5) {
        try {
            String password = strMd5;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public synchronized static boolean checkInternetConnection(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        if (info == null)
            return false;
        if (!info.isConnected())
            return false;
        if (!info.isAvailable())
            return false;
        return true;
    }

    public static String getCodeError(Context context, String errorCode) {
        String descriptionCode = "";
        if (errorCode.equalsIgnoreCase("01")) {
            descriptionCode = context.getString(R.string.error_01);
        } else if (errorCode.equalsIgnoreCase("02")) {
            descriptionCode = context.getString(R.string.error_02);
        } else if (errorCode.equalsIgnoreCase("04")) {
            descriptionCode = context.getString(R.string.error_04);
        } else if (errorCode.equalsIgnoreCase("05")) {
            descriptionCode = context.getString(R.string.error_05);
        } else if (errorCode.equalsIgnoreCase("06")) {
            descriptionCode = context.getString(R.string.error_06);
        } else if (errorCode.equalsIgnoreCase("07")) {
            descriptionCode = context.getString(R.string.error_07);
        } else if (errorCode.equalsIgnoreCase("09")) {
            descriptionCode = context.getString(R.string.error_09);
        } else if (errorCode.equalsIgnoreCase("11")) {
            descriptionCode = context.getString(R.string.error_11);
        } else if (errorCode.equalsIgnoreCase("20")) {
            descriptionCode = context.getString(R.string.error_20);
        } else if (errorCode.equalsIgnoreCase("21")) {
            descriptionCode = context.getString(R.string.error_21);
        } else if (errorCode.equalsIgnoreCase("22")) {
            descriptionCode = context.getString(R.string.error_22);
        } else if (errorCode.equalsIgnoreCase("23")) {
            descriptionCode = context.getString(R.string.error_23);
        } else if (errorCode.equalsIgnoreCase("24")) {
            descriptionCode = context.getString(R.string.error_24);
        } else if (errorCode.equalsIgnoreCase("25")) {
            descriptionCode = context.getString(R.string.error_25);
        } else if (errorCode.equalsIgnoreCase("26")) {
            descriptionCode = context.getString(R.string.error_26);
        } else if (errorCode.equalsIgnoreCase("27")) {
            descriptionCode = context.getString(R.string.error_27);
        } else if (errorCode.equalsIgnoreCase("28")) {
            descriptionCode = context.getString(R.string.error_28);
        } else if (errorCode.equalsIgnoreCase("29")) {
            descriptionCode = context.getString(R.string.error_29);
        } else if (errorCode.equalsIgnoreCase("30")) {
            descriptionCode = context.getString(R.string.error_30);
        } else if (errorCode.equalsIgnoreCase("31")) {
            descriptionCode = context.getString(R.string.error_31);
        } else if (errorCode.equalsIgnoreCase("32")) {
            descriptionCode = context.getString(R.string.error_32);
        } else if (errorCode.equalsIgnoreCase("33")) {
            descriptionCode = context.getString(R.string.error_33);
        }

        return descriptionCode;
    }

}
