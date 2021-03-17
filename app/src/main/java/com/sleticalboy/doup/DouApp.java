package com.sleticalboy.doup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.CrashHandler;
import com.sleticalboy.util.Prefs;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public final class DouApp extends Application implements CrashHandler.OnCrashListener {

    private static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appContext = base;
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        // 生命周期回调
        initLifecycleCallback();

        // SP 初始化
        Prefs.init(this);

        // 初始化极光推送
       // JPushManager.getInstance().initialize(this);

        // 初始化极光 IM
        // JChatManager.getManager().initialize(this);
    }

    private void initLifecycleCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                // JPushManager.getInstance().initialize(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                // JPushManager.getInstance().onResume(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                // JPushManager.getInstance().onPause(activity);
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                // JPushManager.getInstance().disableReceiver(activity);
            }
        });
    }

    @Override
    public void onCrash() {
        restartApp();
    }

    /**
     * 重新启动 app
     */
    private void restartApp() {
        // 在新的任务栈中启动应用
        StartActivity.actionStart(this, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        // 干掉原有的进程
        Process.killProcess(Process.myPid());
    }
}
