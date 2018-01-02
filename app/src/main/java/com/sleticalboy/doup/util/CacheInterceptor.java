package com.sleticalboy.doup.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */

public final class CacheInterceptor implements Interceptor {

    private static final String TAG = "CacheInterceptor";
    private final Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        if (CommonUtils.isNetworkAvailable(mContext)) {
            final Response response = chain.proceed(request);
            final int maxAge = 60;
            final String cacheControl = request.cacheControl().toString();
            Log.d(TAG, "6s load cache" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Params")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            Log.d(TAG, "no network, load cache");
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            final Response response = chain.proceed(request);
            final int maxStale = 60 * 60 * 24 * 30;
            return response.newBuilder()
                    .removeHeader("Param")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
