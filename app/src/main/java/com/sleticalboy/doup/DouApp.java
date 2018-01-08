package com.sleticalboy.doup;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.sleticalboy.doup.jpush.JPushManager;

import org.litepal.LitePal;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class DouApp extends Application {

    private static final String TAG = "DouApp";

    public static WeakReference<Context> sReference;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext() called with: base = [" + base + "]");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        sReference = new WeakReference<>(this);
        init();
    }

    private void init() {
        SDKInitializer.initialize(this);
        LitePal.initialize(this);
        JPushManager.getInstance().initialize(this);
    }
}
