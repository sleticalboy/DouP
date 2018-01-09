package com.sleticalboy.doup.jshare;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class JShareManager {

    private static JShareManager sInstance;
    private static final Object sLock = new Object();

    private JShareManager() {
    }

    public static JShareManager getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new JShareManager();
                }
            }
        }
        return sInstance;
    }

    public void initialize(@NonNull Context context) {
        //
    }
}
