package com.sleticalboy.doup.jshare;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class JShareManager {

    private static JShareManager sManager;
    private static final Object LOCK = new Object();

    private JShareManager() {
    }

    public static JShareManager getInstance() {
        if (sManager == null) {
            synchronized (LOCK) {
                if (sManager == null) {
                    sManager = new JShareManager();
                }
            }
        }
        return sManager;
    }

    public void initialize(@NonNull Context context) {
        //
    }
}
