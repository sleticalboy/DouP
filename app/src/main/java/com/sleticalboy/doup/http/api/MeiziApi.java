package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.meizi.BeautyBean;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public interface MeiziApi {

    @GET("data/福利/10/{page}")
    rx.Observable<BeautyBean> getBeauty(@Path("page") int page);
}
