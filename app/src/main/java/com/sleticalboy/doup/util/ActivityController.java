package com.sleticalboy.doup.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class ActivityController {

    private static List<Activity> sActivityList = new ArrayList<>();

    public static void add(Activity a) {
        if (!sActivityList.contains(a))
            sActivityList.add(a);
    }

    public static void remove(Activity a) {
        if (sActivityList.contains(a))
            sActivityList.remove(a);
    }

    public static void finishAll() {
        for (Activity a : sActivityList) {
            if (!a.isFinishing())
                a.finish();
        }
        sActivityList.clear();
    }
}
