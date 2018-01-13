package com.sleticalboy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class SPUtils {

    private static SharedPreferences sSharedPreferences;

    private SPUtils() {
    }

    public static void init(@NonNull Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void putInt(String key, int value) {
        sSharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return sSharedPreferences.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        sSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        sSharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return sSharedPreferences.getString(key, defValue);
    }
}
