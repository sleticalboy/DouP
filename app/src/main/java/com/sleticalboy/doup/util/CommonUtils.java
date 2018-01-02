package com.sleticalboy.doup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class CommonUtils {

    /**
     * 将视频时长转换成 12'19" 05'04" 的形式
     */
    public static String wrapperTime(int duration) {
        if (duration < 0)
            throw new IllegalArgumentException("time can not be negative");
        StringBuilder builder = new StringBuilder();
        int minutes = duration / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            if (hours < 24) {
                builder.append(hours).append(":");
            }
            minutes %= 60;
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        } else {
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        }
        int seconds = duration % 60;
        if (seconds <= 9)
            builder.append(0).append(seconds).append("\"");
        else
            builder.append(seconds).append("\"");
        return builder.toString();
    }

    /**
     * 获取应用的缓存目录
     */
    public static File getCacheDir(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        return context.getApplicationContext().getCacheDir();
    }

    /**
     * 判断网络是否连接
     *
     * @return 连接返回 true， 否则 false
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info.isAvailable())
                return true;
        }
        return false;
    }
}
