package com.sleticalboy.doup.http;

import com.sleticalboy.doup.http.api.BeautyApi;
import com.sleticalboy.doup.http.api.NewsApi;
import com.sleticalboy.doup.util.CommonUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private static RetrofitClient sClient;

    //    private static final String BASE_BOOK_URL = "";
//    private static final String BASE_MOVIE_URL = "";

    private static final String BASE_BEAUTY_URL = "http://gank.io/api/";
    private static final String BASE_NEWS_URL = "http://news-at.zhihu.com/api/4/";

    private static final long MAX_CACHE_SIZE = 1L << 24;
    private static final String CACHE_DIR = "cache_dir";

    //    private BookApi bookApiService;
//    private MovieApi movieApiService;

    private NewsApi newsApiService;
    private BeautyApi beautyApiService;

    public BeautyApi getBeautyApiService() {
        return beautyApiService;
    }

    public NewsApi getNewsApiService() {
        return newsApiService;
    }

    private RetrofitClient() {
        File cacheDir = new File(CommonUtils.getCacheDir(), CACHE_DIR);
        Cache cache = new Cache(cacheDir, MAX_CACHE_SIZE);

        Interceptor cacheControlInterceptor = chain -> {
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)
                    .maxStale(30, TimeUnit.DAYS)
                    .build();

            Request request = chain.request();

            if (!CommonUtils.isNetworkAvailable()) {
                request = chain.request()
                        .newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }

            Response oriResponse = chain.proceed(request);

            if (CommonUtils.isNetworkAvailable()) {
                int maxAge = 0;
                return oriResponse.newBuilder()
                        .removeHeader("Param")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 30;
                return oriResponse.newBuilder()
                        .removeHeader("Param")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(cacheControlInterceptor)
                .cache(cache)
                .build();

        newsApiService = buildRetrofit(BASE_NEWS_URL, client).create(NewsApi.class);
        beautyApiService = buildRetrofit(BASE_BEAUTY_URL, client).create(BeautyApi.class);
//        bookApiService = buildRetrofit(BASE_BOOK_URL, client).create(BookApi.class);
//        movieApiService = buildRetrofit(BASE_MOVIE_URL, client).create(MovieApi.class);
    }

    private Retrofit buildRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public synchronized static RetrofitClient getClient() {
        if (sClient == null) {
            sClient = new RetrofitClient();
        }
        return sClient;
    }
}
