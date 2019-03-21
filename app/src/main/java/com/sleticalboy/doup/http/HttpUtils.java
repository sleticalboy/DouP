package com.sleticalboy.doup.http;

import com.sleticalboy.util.StrUtils;

import okhttp3.Request;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public final class HttpUtils {

    private HttpUtils() {
        throw new AssertionError();
    }

    public static void request(String completeUrl, okhttp3.Callback callback) {
        if (StrUtils.isEmpty(completeUrl) || callback == null) {
            throw new IllegalArgumentException("completeUrl or callback is null");
        }

        Request request = new Request.Builder()
                .url(completeUrl) // 默认就是 GET 方法
                .build();

        RetrofitClient.http()
                .newCall(request)
                .enqueue(callback);
    }
}
