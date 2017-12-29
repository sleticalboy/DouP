package com.sleticalboy.doup.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class AutoUpdateService extends IntentService {

    public AutoUpdateService() {
        super("AutoUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
