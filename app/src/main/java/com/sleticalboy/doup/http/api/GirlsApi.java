package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.http.HttpConfig;
import com.sleticalboy.doup.bean.girl.GirlBean;

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

    @Headers({HttpConfig.HEADER_KEY + ":" + HttpConfig.HEADER_VALUE_MEIZI})
    @GET("data/福利/10/{page}") // http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    Observable<GirlBean> getBeauty(@Path("page") int page);
}
