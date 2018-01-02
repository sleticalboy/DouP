package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.mvp.model.bean.news.NewsBean;
import com.sleticalboy.doup.mvp.model.bean.news.NewsDetailBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.sleticalboy.doup.http.HttpConfig.HEADER_KEY;
import static com.sleticalboy.doup.http.HttpConfig.HEADER_VALUE_NEWS;

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
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_NEWS})
    @GET("4/news/latest")
    rx.Observable<NewsBean> getLatestNews();

    /**
     * 获取之前的新闻列表
     *
     * @param date 日期
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_NEWS})
    @GET("4/news/before/{date}")
    rx.Observable<NewsBean> getOldNews(@Path("date") String date);

    /**
     * 获取新闻详情
     *
     * @param id 新闻 id
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_NEWS})
    @GET("4/news/{id}")
    rx.Observable<NewsDetailBean> getNewsDetail(@Path("id") String id);
}
