package com.sleticalboy.doup.http;

import com.sleticalboy.util.StrUtils;

import okhttp3.Request;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class HttpUtils {

    public static void request(String completeUrl, okhttp3.Callback callback) {
        if (StrUtils.isEmpty(completeUrl) || callback == null)
            throw new IllegalArgumentException("completeUrl or callback is null");

        Request request = new Request.Builder()
                .url(completeUrl)
                .build();

        RetrofitClient.getInstance().getOkHttpClient()
                .newCall(request)
                .enqueue(callback);
    }
}
