package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.mvp.model.bean.eye.FindingBean;
import com.sleticalboy.doup.mvp.model.bean.eye.PopularBean;
import com.sleticalboy.doup.mvp.model.bean.eye.RecommendBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.sleticalboy.doup.http.HttpConfig.HEADER_KEY;
import static com.sleticalboy.doup.http.HttpConfig.HEADER_VALUE_EYES;

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
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<RecommendBean> getRecommend();

    /**
     * 获取更多推荐内容
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("v2/feed")
    Observable<RecommendBean> getMoreRecommend(@Query("date") String date,
                                                  @Query("num") String num);

    /**
     * 获取发现内容
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<List<FindingBean>> getFindings();

    /**
     * 获取热门推荐内容
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("api/v3/ranklist")
    Observable<PopularBean> getPopular(@Query("num") int num,
                                          @Query("strategy") String strategy,
                                          @Query("udid") String udid,
                                          @Query("vc") String vc);

    /**
     * 关键词搜索
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("v1/search")
    Observable<PopularBean> getSearchData(@Query("num") int num,
                                             @Query("query") String query,
                                             @Query("start") int start);

    /**
     * 发现详情页面
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_EYES})
    @GET("v3/videos")
    Observable<PopularBean> getFindingsDetail(@Query("categoryName") String categoryName,
                                                 @Query("strategy") String strategy,
                                                 @Query("udid") String udid,
                                                 @Query("vc") int vc);
}
