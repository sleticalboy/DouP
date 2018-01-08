package com.sleticalboy.doup.jpush;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.sleticalboy.doup.BuildConfig;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushReceiver;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class JPushManager {

    private static final String TAG = "JPushManager";

    private static final Object sLock = new Object();

    private static JPushManager sInstance;
    private Context mContext;

    private JPushManager() {
    }

    public static JPushManager getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new JPushManager();
                }
            }
        }
        return sInstance;
    }

    public void initialize(@NonNull Context context) {
        mContext = context;
        if (BuildConfig.DEBUG) {
            JPushInterface.setDebugMode(true);
        } else {
            JPushInterface.setDebugMode(false);
        }
        JPushInterface.init(context);
    }

    /**
     * 开启极光推送
     *
     * @param name Component name
     */
    public void enable(ComponentName name) {
        PackageManager pm = mContext.getPackageManager();
        pm.setComponentEnabledSetting(name,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 关闭极光推送
     *
     * @param name Component name
     */
    public void disable(ComponentName name) {
        PackageManager pm = mContext.getPackageManager();
        pm.setComponentEnabledSetting(name,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 开启机关通知
     */
    public void enableReceiver() {
        enable(new ComponentName(mContext, PushReceiver.class));
        enable(new ComponentName(mContext, MyJPushMessageReceiver.class));
    }

    /**
     * 关闭极光通知
     */
    public void disableReceiver() {
        disable(new ComponentName(mContext, PushReceiver.class));
        disable(new ComponentName(mContext, MyJPushMessageReceiver.class));
    }

    public void onResume() {
        JPushInterface.onResume(mContext);
    }

    public void onPause() {
        JPushInterface.onPause(mContext);
    }
}
