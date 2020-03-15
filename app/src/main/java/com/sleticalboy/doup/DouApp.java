package com.sleticalboy.doup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.sleticalboy.base.LifecycleCallback;
import com.sleticalboy.base.LifecycleController;
import com.sleticalboy.doup.db.DBController;
import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.CrashHandler;
import com.sleticalboy.util.SPUtils;

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
        SPUtils.INSTANCE.init(this);

        // 初始化 GreenDao 数据库, 是否加密
        DBController.getInstance().defaultInitDB(this, false);

        // 初始化极光推送
//        JPushManager.getInstance().initialize(this);

        // 初始化极光 IM
        // JChatManager.getManager().initialize(this);
    }

    private void initLifecycleCallback() {
        LifecycleController.Companion.getInstance().setLifecycleCallback(new LifecycleCallback() {
            @Override
            public void onCreate(@NonNull Activity activity, Bundle savedInstanceState) {
//                JPushManager.getInstance().initialize(activity);
            }

            @Override
            public void onActivityStart(@NonNull Activity activity) {

            }

            @Override
            public void onStartActivityForResult(@NonNull Activity activity) {

            }

            @Override
            public void onStartActivity(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResume(@NonNull Activity activity) {
//                JPushManager.getInstance().onResume(activity);
            }

            @Override
            public void onActivityPause(@NonNull Activity activity) {
//                JPushManager.getInstance().onPause(activity);
            }

            @Override
            public void onActivityStop(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroy(@NonNull Activity activity) {
//                JPushManager.getInstance().disableReceiver(activity);
            }

            @Override
            public void onActivityFinish(@NonNull Activity activity) {

            }

            @Override
            public void onActivityConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onActivityRestoreInstanceState(@NonNull Activity activity, Bundle saveInstanceState) {

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
        Intent intent = new Intent(this, StartActivity.class);
//         在新的任务栈中启动应用
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // 干掉原有的进程
        Process.killProcess(Process.myPid());
    }
}
