package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.girl.GirlBean;
import com.sleticalboy.doup.http.HttpConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public interface GirlsApi {

    /**
     * http://gank.io/api/data/福利/10/1
     *
     * @param page
     * @return
     */
    @Headers({HttpConfig.HEADER_KEY + ":" + HttpConfig.HEADER_VALUE_MEIZI})
    @GET("api/data/福利/10/{page}")
    Observable<GirlBean> getBeauty(@Path("page") int page);
}
