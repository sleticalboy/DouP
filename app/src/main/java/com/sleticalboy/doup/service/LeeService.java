package com.sleticalboy.doup.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class LeeService extends IntentService {

    private static final String ACTION_FOO = "com.sleticalboy.doup.service.action.FOO";
    private static final String ACTION_BAZ = "com.sleticalboy.doup.service.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.sleticalboy.doup.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.sleticalboy.doup.service.extra.PARAM2";

    public LeeService() {
        super("LeeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
            }
        }
    }
}
