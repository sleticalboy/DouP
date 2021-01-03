package com.sleticalboy.util

import android.util.Log
import java.lang.Exception

/**
 * Created by Android Studio.
 * Date: 10/29/17.
 *
 * @author sleticalboy
 */
object LogUtils {

    private val DEBUG = Log.isLoggable("LogUtils", Log.DEBUG)

    @JvmStatic
    fun v(tag: String, msg: String) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.v(tag, msg)
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.d(tag, msg)
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.i(tag, msg)
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (!BuildConfig.DEBUG || DEBUG) {
            if (BuildConfig.DEBUG) {
                Log.w(tag, msg)
            }
        }
    }

    @JvmStatic
    fun w(tag: String, tr: Throwable) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.w(tag, Log.getStackTraceString(tr))
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.e(tag, msg)
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String, e: Exception) {
        if (!BuildConfig.DEBUG || DEBUG) {
            Log.e(tag, msg, e)
        }
    }
}
