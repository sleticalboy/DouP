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

    private var sp: SharedPreferences? = null

    fun init(context: Context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun putInt(key: String, value: Int) {
        sp!!.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return sp!!.getInt(key, defValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sp!!.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sp!!.getBoolean(key, defValue)
    }

    fun putString(key: String, value: String) {
        sp!!.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String?): String? {
        return sp!!.getString(key, defValue)
    }
}
