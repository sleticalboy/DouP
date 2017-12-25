package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.bean.news.NewsDetailBean;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public interface NewsApi {

    /**
     * 获取最新新闻列表
     */
    @GET("news/latest")
    rx.Observable<NewsBean> getLatestNews();

    /**
     * 获取之前的新闻列表
     *
     * @param date 日期
     */
    @GET("news/before/{date}")
    rx.Observable<NewsBean> getOldNews(@Path("date") String date);

    /**
     * 获取新闻详情
     *
     * @param id 新闻 id
     */
    @GET("news/{id}")
    rx.Observable<NewsDetailBean> getNewsDetail(@Path("id") String id);
}
