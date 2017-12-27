package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.eye.FindingBean;
import com.sleticalboy.doup.bean.eye.PopularBean;
import com.sleticalboy.doup.bean.eye.RecommendBean;

import retrofit2.http.GET;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public interface EyesApi {

    /**
     * 获取推荐内容
     */
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    rx.Observable<RecommendBean> getRecommend();

    /**
     * 获取发现内容
     */
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    rx.Observable<FindingBean> getFindings();

    /**
     * 获取热门推荐内容
     */
    @GET("api/v3/ranklist")
    rx.Observable<PopularBean> getPopular();

}
