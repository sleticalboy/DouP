package com.sleticalboy.doup.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sleticalboy.doup.BuildConfig;
import com.sleticalboy.doup.DouApp;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public final class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static final long MAX_CACHE_SIZE = 1L << 24;
    private static final String CACHE_DIR = "app_cache";

    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;

    private RetrofitClient() {
        mOkHttpClient = httpBuilder().build();
        mRetrofit = retrofitBuilder().build();
    }

    private Retrofit.Builder retrofitBuilder() {
        // 创建 Retrofit.Builder
        return new Retrofit.Builder()
                .baseUrl("http://www.sample.com") // placeholder
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    private OkHttpClient.Builder httpBuilder() {
        Cache cache = new Cache(new File(DouApp.getContext().getCacheDir(), CACHE_DIR), MAX_CACHE_SIZE);
        // 创建 OkHttpClient
        final HttpLogInterceptor loggerInterceptor = new HttpLogInterceptor();
        if (BuildConfig.DEBUG) {
            loggerInterceptor.setLevel(HttpLogInterceptor.Level.BODY);
        }
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggerInterceptor)
                .addInterceptor(new UrlChangeInterceptor())
                .addInterceptor(new CacheInterceptor());
    }

    public static RetrofitClient retrofit() {
        return SingleHolder.RETROFIT_CLIENT;
    }

    public static OkHttpClient http() {
        return retrofit().mOkHttpClient;
    }

    public final <T> T create(Class<T> service) {
        if (service == null) {
            throw new NullPointerException("Api service is null");
        }
        return mRetrofit.create(service);
    }

    private final static class SingleHolder {
        static final RetrofitClient RETROFIT_CLIENT = new RetrofitClient();
    }
}