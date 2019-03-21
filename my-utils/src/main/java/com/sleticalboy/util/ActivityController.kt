package com.sleticalboy.util

import android.app.Activity
import java.util.*

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
object ActivityController {

    private val activityList = ArrayList<Activity>()

    fun add(activity: Activity) {
        if (!activityList.contains(activity))
            activityList.add(activity)
    }

    fun remove(activity: Activity) {
        if (activityList.contains(activity))
            activityList.remove(activity)
    }

    fun finishAll() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activityList.clear()
    }
}
