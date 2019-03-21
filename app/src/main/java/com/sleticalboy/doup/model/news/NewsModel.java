package com.sleticalboy.doup.model.news;

import android.content.Context;

import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.bean.news.NewsDetailBean;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.NewsApi;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public final class NewsModel {

    private WeakReference<Context> mWeakReference;
    private NewsApi mNewsApiService;

    public NewsModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        // RetrofitClient client = RetrofitClient.retrofit(mWeakReference.get(), ApiConstant.BASE_NEWS_URL);
        mNewsApiService = RetrofitClient.retrofit().create(NewsApi.class);
    }

    public Observable<NewsBean> getLatestNews() {
        return mNewsApiService.getLatestNews();
    }

    public Observable<NewsBean> getOldNews(String date) {
        return mNewsApiService.getOldNews(date);
    }

    public Observable<NewsDetailBean> getNewsDetail(String id) {
        return mNewsApiService.getNewsDetail(id);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
