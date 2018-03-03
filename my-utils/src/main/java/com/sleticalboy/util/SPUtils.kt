package com.sleticalboy.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
object SPUtils {

    private var sSharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun putInt(key: String, value: Int) {
        sSharedPreferences!!.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return sSharedPreferences!!.getInt(key, defValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sSharedPreferences!!.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sSharedPreferences!!.getBoolean(key, defValue)
    }

    fun putString(key: String, value: String) {
        sSharedPreferences!!.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String): String? {
        return sSharedPreferences!!.getString(key, defValue)
    }
}
