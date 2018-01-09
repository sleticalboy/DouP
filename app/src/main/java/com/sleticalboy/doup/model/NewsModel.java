package com.sleticalboy.doup.model;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.NewsApi;
import com.sleticalboy.doup.model.news.NewsBean;
import com.sleticalboy.doup.model.news.NewsDetailBean;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class NewsModel {

    private WeakReference<Context> mWeakReference;
    private NewsApi mNewsApiService;

    public NewsModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_NEWS_URL);
        mNewsApiService = client.create(NewsApi.class);
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
