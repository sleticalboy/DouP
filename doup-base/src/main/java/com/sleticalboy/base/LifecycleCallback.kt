package com.sleticalboy.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle

/**
 * Created on 18-1-30.
 *
 * @author sleticalboy
 * @version 1.0
 * @description Activity 生命周期回调
 */
interface LifecycleCallback {

    fun onCreate(activity: Activity, savedInstanceState: Bundle)

    fun onActivityStart(activity: Activity)

    fun onStartActivityForResult(activity: Activity)

    fun onStartActivity(activity: Activity)

    fun onActivityResume(activity: Activity)

    fun onActivityPause(activity: Activity)

    fun onActivityStop(activity: Activity)

    fun onActivitySaveInstanceState(activity: Activity, outState: Bundle)

    fun onActivityDestroy(activity: Activity)

    fun onActivityFinish(activity: Activity)

    fun onActivityConfigurationChanged(newConfig: Configuration)

    fun onActivityRestoreInstanceState(saveInstanceState: Bundle)
}
