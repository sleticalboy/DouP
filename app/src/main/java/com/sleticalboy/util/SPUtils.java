package com.sleticalboy.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sleticalboy.doup.DouApp;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class SPUtils {

    private static SharedPreferences sInstance = PreferenceManager
            .getDefaultSharedPreferences(DouApp.sReference.get());

    public static void putInt(String key, int value) {
        sInstance.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return sInstance.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        sInstance.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sInstance.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        sInstance.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return sInstance.getString(key, defValue);
    }
}
