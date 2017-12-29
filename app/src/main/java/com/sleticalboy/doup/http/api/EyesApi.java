package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.eye.FindingBean;
import com.sleticalboy.doup.bean.eye.PopularBean;
import com.sleticalboy.doup.bean.eye.RecommendBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public interface EyesApi {

    /**
     * 获取推荐首页内容
     */
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    rx.Observable<RecommendBean> getRecommend();

    /**
     * 获取更多推荐内容
     */
    @GET("v2/feed")
    rx.Observable<RecommendBean> getMoreRecommend(@Query("date") String date,
                                                  @Query("num") String num);

    /**
     * 获取发现内容
     */
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    rx.Observable<List<FindingBean>> getFindings();

    /**
     * 获取热门推荐内容
     */
    @GET("api/v3/ranklist")
    rx.Observable<PopularBean> getPopular(@Query("num") int num,
                                          @Query("strategy") String strategy,
                                          @Query("udid") String udid,
                                          @Query("vc") String vc);

    /**
     * 关键词搜索
     */
    @GET("v1/search")
    rx.Observable<PopularBean> getSearchData(@Query("num") int num,
                                             @Query("query") String query,
                                             @Query("start") int start);

    @GET("v3/videos")
    rx.Observable<PopularBean> getFindingsDetail(@Query("categoryName") String categoryName,
                                                 @Query("strategy") String strategy,
                                                 @Query("udid") String udid,
                                                 @Query("vc") int vc);
}
