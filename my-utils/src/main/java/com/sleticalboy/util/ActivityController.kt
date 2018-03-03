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

    fun add(a: Activity) {
        if (!sActivityList.contains(a))
            sActivityList.add(a)
    }

    fun remove(a: Activity) {
        if (sActivityList.contains(a))
            sActivityList.remove(a)
    }

    fun finishAll() {
        for (a in sActivityList) {
            if (!a.isFinishing)
                a.finish()
        }
        sActivityList.clear()
    }
}
