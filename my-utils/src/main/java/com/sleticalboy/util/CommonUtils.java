package com.sleticalboy.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public final class CommonUtils {
    
    /**
     * 将视频时长转换成 12'19" 05'04" 的形式
     */
    public static String wrapperTime(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("time can not be negative");
        StringBuilder builder = new StringBuilder();
        int minutes = duration / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            if (hours < 24) {
                builder.append(hours).append(":");
            }
            minutes %= 60;
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        } else {
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        }
        int seconds = duration % 60;
        if (seconds <= 9)
            builder.append(0).append(seconds).append("\"");
        else
            builder.append(seconds).append("\"");
        return builder.toString();
    }

    /**
     * 获取应用的缓存目录
     */
    public static File getCacheDir() {
        return ContextProvider.getAppContext().getCacheDir();
    }

    /**
     * 判断网络是否连接
     *
     * @return 连接返回 true， 否则 false
     */
    public static boolean isNetworkAvailable() {
        final Context context = ContextProvider.getAppContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info.isAvailable())
                return true;
        }
        return false;
    }

    public static boolean isConnected() {
        final Context context = ContextProvider.getAppContext();
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conn != null;
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getImei(String defValue) {
        final Context context = ContextProvider.getAppContext();
        String ret = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            assert telephonyManager != null;
            ret = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(StrUtils.class.getSimpleName(), e.getMessage());
        }
        if (StrUtils.isReadableASCII(ret)) {
            return ret;
        } else {
            return defValue;
        }
    }

    /**
     * 取得版本号
     *
     * @return 版本号
     */
    public static String getVersion() {
        final Context context = ContextProvider.getAppContext();
        try {
            PackageInfo manager = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }
}
