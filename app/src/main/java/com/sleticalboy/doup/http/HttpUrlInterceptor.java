package com.sleticalboy.doup.http;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class HttpUrlInterceptor implements Interceptor {

    private static final String TAG = "HttpUrlInterceptor";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers(HttpConfig.HEADER_KEY);
        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oriBaseUrl = request.url();
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(HttpConfig.HEADER_KEY);

            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            Log.d(TAG, headerValue);
            HttpUrl newBaseUrl;
            if (HttpConfig.HEADER_VALUE_NEWS.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpConfig.BASE_URL_NEWS);
            } else if (HttpConfig.HEADER_VALUE_MEIZI.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpConfig.BASE_URL_MEIZI);
            } else if (HttpConfig.HEADER_VALUE_EYES.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpConfig.BASE_URL_EYES);
            } else if (HttpConfig.HEADER_VALUE_WEATHER.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(HttpConfig.BASE_URL_WEATHER);
            } else {
                newBaseUrl = oriBaseUrl;
            }

            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oriBaseUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();

            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(builder.url(newFullUrl).build());
        } else {
            return chain.proceed(request);
        }
    }
}
