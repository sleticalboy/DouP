package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.mvp.model.bean.meizi.BeautyBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.sleticalboy.doup.http.HttpConfig.HEADER_VALUE_MEIZI;
import static com.sleticalboy.doup.http.HttpConfig.HEADER_KEY;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public interface MeiziApi {

    @Headers({HEADER_KEY + ":" + HEADER_VALUE_MEIZI})
    @GET("data/福利/10/{page}") // http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    rx.Observable<BeautyBean> getBeauty(@Path("page") int page);
}
