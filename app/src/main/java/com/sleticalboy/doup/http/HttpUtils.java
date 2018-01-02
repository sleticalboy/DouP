package com.sleticalboy.doup.http;

import android.text.TextUtils;

import okhttp3.Request;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class HttpUtils {

    public static void request(String completeUrl, okhttp3.Callback callback) {
        if (TextUtils.isEmpty(completeUrl) || callback == null)
            throw new IllegalArgumentException("completeUrl or callback is null");

        Request request = new Request.Builder()
                .url(completeUrl)
                .build();

        RetrofitClient.getInstance(null, null).getOkHttpClient()
                .newCall(request)
                .enqueue(callback);
    }
}
