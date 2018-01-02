package com.sleticalboy.doup.http;

import android.content.Context;
import android.util.Log;

import com.sleticalboy.doup.util.CacheInterceptor;

import java.io.File;
import java.lang.ref.WeakReference;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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

    private static RetrofitClient sClient;

    private File mHttpCacheDir;
    private Cache mCache;
    private static OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    private WeakReference<Context> mWeakReference;

    private RetrofitClient(Context context, String baseUrl) {
        mWeakReference = new WeakReference<>(context);

        if (mHttpCacheDir == null) {
            mHttpCacheDir = new File(mWeakReference.get().getCacheDir(), CACHE_DIR);
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
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(mCache)
                .addInterceptor(new CacheInterceptor(mWeakReference.get()))
                .addInterceptor(new CacheInterceptor(mWeakReference.get()))
                .build();

        // 创建 Retrofit.Builder
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public synchronized static RetrofitClient getInstance(Context context, String baseUrl) {
        if (sClient == null) {
            sClient = new RetrofitClient(context, baseUrl);
        }
        return sClient;
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