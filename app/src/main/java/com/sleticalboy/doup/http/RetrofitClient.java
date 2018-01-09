package com.sleticalboy.doup.http;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sleticalboy.doup.util.CommonUtils;

import java.io.File;
import java.lang.ref.WeakReference;

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

    private static RetrofitClient sInstance;

    private File mHttpCacheDir;
    private Cache mCache;
    private static OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    private WeakReference<Context> mWeakReference;

    private RetrofitClient(Context context, String baseUrl) {
        mWeakReference = new WeakReference<>(context);

        if (mHttpCacheDir == null) {
            mHttpCacheDir = new File(CommonUtils.getCacheDir(mWeakReference.get()), CACHE_DIR);
        }

        try {
            if (mCache == null) {
                mCache = new Cache(mHttpCacheDir, MAX_CACHE_SIZE);
            }
        } catch (Exception e) {
            Log.d(TAG, "could not create http cache", e);
        }

        // 创建 OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                .cache(mCache)
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)) // 打印网络请求日志
                .addInterceptor(new UrlChangeInterceptor()) // 动态改变 baseUrl
                .addInterceptor(new CacheInterceptor(mWeakReference.get())) // 缓存功能
                .build();

        // 创建 Retrofit.Builder
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public synchronized static RetrofitClient getInstance(Context context, String baseUrl) {
        if (sInstance == null) {
            sInstance = new RetrofitClient(context, baseUrl);
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public <T> T create(Class<T> service) {
        if (service == null)
            throw new RuntimeException("Api service is null");
        return mRetrofit.create(service);
    }

    public void clear() {
        mWeakReference.clear();
        mWeakReference = null;
    }
}