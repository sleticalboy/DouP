package com.sleticalboy.doup.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sleticalboy.util.CommonUtils;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static final long MAX_CACHE_SIZE = 1L << 24;
    private static final String CACHE_DIR = "app_cache";

    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;

    private RetrofitClient() {
        Cache cache = new Cache(new File(CommonUtils.getCacheDir(), CACHE_DIR), MAX_CACHE_SIZE);
        // 创建 OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)) // 打印网络请求日志
                .addInterceptor(new UrlChangeInterceptor()) // 动态改变 baseUrl
                .addInterceptor(new CacheInterceptor()) // 缓存功能
                .build();

        // 创建 Retrofit.Builder
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.sample.com")
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        return SingleHolder.RETROFIT_CLIENT;
    }
    
    private final static class SingleHolder {
        static final RetrofitClient RETROFIT_CLIENT = new RetrofitClient();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public <T> T create(Class<T> service) {
        if (service == null)
            throw new RuntimeException("Api service is null");
        return mRetrofit.create(service);
    }
}