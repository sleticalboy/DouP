package com.sleticalboy.doup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sleticalboy.doup.DouApp;

import java.io.File;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class CommonUtils {

    /**
     * 获取应用的缓存目录
     */
    public static File getCacheDir() {
        return DouApp.sContext.getCacheDir();
    }

    /**
     * 判断网络是否连接
     *
     * @return 连接返回 true， 否则 false
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) DouApp.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info.isAvailable();
        }
        return false;
    }
}
