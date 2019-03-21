package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.bean.news.NewsDetailBean;
import com.sleticalboy.doup.http.HttpConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    @Headers({HttpConfig.HEADER_KEY + ": " + HttpConfig.HEADER_VALUE_NEWS})
    @GET("api/4/news/latest")
    Observable<NewsBean> getLatestNews();

    /**
     * 获取之前的新闻列表
     *
     * @param date 日期
     */
    @Headers({HttpConfig.HEADER_KEY + ": " + HttpConfig.HEADER_VALUE_NEWS})
    @GET("api/4/news/before/{date}")
    Observable<NewsBean> getOldNews(@Path("date") String date);

    /**
     * 获取新闻详情
     *
     * @param id 新闻 id
     */
    @Headers({HttpConfig.HEADER_KEY + ": " + HttpConfig.HEADER_VALUE_NEWS})
    @GET("api/4/news/{id}")
    Observable<NewsDetailBean> getNewsDetail(@Path("id") String id);
}
