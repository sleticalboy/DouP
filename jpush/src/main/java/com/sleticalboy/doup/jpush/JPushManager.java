package com.sleticalboy.doup.jpush;

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

    public static void enable() {
        //
    }

    public static void disable() {
        //
    }
}
