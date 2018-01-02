package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.bean.news.NewsDetailBean;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.NewsApi;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public rx.Observable<NewsBean> getLatestNews() {
        return mNewsApiService.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<NewsBean> getOldNews(String date) {
        return mNewsApiService.getOldNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NewsDetailBean> getNewsDetail(String id) {
        return mNewsApiService.getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
