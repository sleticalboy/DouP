package com.sleticalboy.doup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.sleticalboy.base.LifecycleCallback;
import com.sleticalboy.base.LifecycleController;
import com.sleticalboy.doup.jpush.JPushManager;
import com.sleticalboy.doup.model.weather.DaoMaster;
import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.CrashHandler;
import com.sleticalboy.util.SPUtils;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public final class DouApp extends Application implements CrashHandler.OnCrashListener {

    private static final String TAG = "DouApp";
    private static final boolean ENCRYPTED = false;

    private static WeakReference<Context> sReference;
    private static AbstractDaoSession sDaoSession;

    public static Context getContext() {
        return sReference.get();
    }

    public static AbstractDaoSession getDaoSession() {
        return sDaoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(TAG, "attachBaseContext() called with: base = [" + base + "]");
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        sReference = new WeakReference<>(this);
        init();
    }

    private void init() {
        // 生命周期回调
        initLifecycleCallback();

        // SP 初始化
        SPUtils.INSTANCE.init(this);

        // 初始化 GreenDao 数据库
        initDB();

        // 初始化极光推送
        JPushManager.getInstance().initialize(this);

        // 初始化极光 IM
        // JChatManager.getManager().initialize(this);
    }

    private void initLifecycleCallback() {
        LifecycleController.Companion.getInstance().setLifecycleCallback(new LifecycleCallback() {
            @Override
            public void onCreate(Activity activity, Bundle savedInstanceState) {
                JPushManager.getInstance().initialize(activity);
            }

            @Override
            public void onActivityStart(Activity activity) {

            }

            @Override
            public void onStartActivityForResult(Activity activity) {

            }

            @Override
            public void onStartActivity(Activity activity) {

            }

            @Override
            public void onActivityResume(Activity activity) {
                JPushManager.getInstance().onResume(activity);
            }

            @Override
            public void onActivityPause(Activity activity) {
                JPushManager.getInstance().onPause(activity);
            }

            @Override
            public void onActivityStop(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroy(Activity activity) {
                JPushManager.getInstance().disableReceiver(activity);
            }

            @Override
            public void onActivityFinish(Activity activity) {

            }

            @Override
            public void onActivityConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onActivityRestoreInstanceState(Activity activity, Bundle saveInstanceState) {

            }
        });
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                this, ENCRYPTED ? "encrypt-db" : "common-db");
        Database database = ENCRYPTED
                ? helper.getEncryptedReadableDb("super-secret")
                : helper.getWritableDb();
        sDaoSession = new DaoMaster(database).newSession();
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
        // 在新的任务栈中启动应用
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // 干掉原有的进程
        Process.killProcess(Process.myPid());
    }
}
