package com.sleticalboy.util

import android.app.Activity

import java.util.ArrayList

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
object ActivityController {

    private val sActivityList = ArrayList<Activity>()

    fun add(activity: Activity) {
        if (!sActivityList.contains(activity))
            sActivityList.add(activity)
    }

    fun remove(activity: Activity) {
        if (sActivityList.contains(activity))
            sActivityList.remove(activity)
    }

    fun finishAll() {
        sActivityList
                .filterNot { it.isFinishing }
                .forEach { it.finish() }
        sActivityList.clear()
    }
}
