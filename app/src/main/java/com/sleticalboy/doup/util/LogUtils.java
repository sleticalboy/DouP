package com.sleticalboy.doup.util;

import android.util.Log;

import com.sleticalboy.doup.BuildConfig;

/**
 * Created by Android Studio.
 * Date: 10/29/17.
 *
 * @author sleticalboy
 */
public class LogUtils {

    private static final boolean DEBUG = Log.isLoggable(LogUtils.class.getSimpleName(), Log.DEBUG);

    public static void v(String tag, String msg) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (!BuildConfig.DEBUG || DEBUG) {
            if (BuildConfig.DEBUG) {
                Log.w(tag, msg);
            }
        }
    }

    public static void w(String tag, Throwable tr) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.w(tag, Log.getStackTraceString(tr));
        }
    }

    public static void e(String tag, String msg) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Exception e) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.e(tag, msg, e);
        }
    }
}
