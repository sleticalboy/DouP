package com.sleticalboy.doup;

import android.app.Application;
import android.content.Context;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class DouApp extends Application {

    public static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
