package com.sleticalboy.doup.jpush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

public class JPushUtil {

    public static final String PREFS_NAME = "JPUSH_EXAMPLE";
    public static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
    public static final String PREFS_START_TIME = "PREFS_START_TIME";
    public static final String PREFS_END_TIME = "PREFS_END_TIME";
    public static final String KEY_APP_KEY = "JPUSH_APPKEY";

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     *
     * @param s 要校验的字符串
     * @return true 符合规则，否则false
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 取得 AppKey
     *
     * @return appkey
     */
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appKey;
    }

    /**
     * 取得版本号
     *
     * @return 版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    public static void showToast(final String toast, final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conn != null;
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI(Context context, String defValue) {
        String ret = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            assert telephonyManager != null;
            ret = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(JPushUtil.class.getSimpleName(), e.getMessage());
        }
        if (isReadableASCII(ret)) {
            return ret;
        } else {
            return defValue;
        }
    }

    private static boolean isReadableASCII(CharSequence string) {
        if (TextUtils.isEmpty(string))
            return false;
        try {
            Pattern p = Pattern.compile("[\\x20-\\x7E]+");
            return p.matcher(string).matches();
        } catch (Throwable e) {
            return true;
        }
    }

    public static String getDeviceId(Context context) {
        return JPushInterface.getUdid(context);
    }
}
