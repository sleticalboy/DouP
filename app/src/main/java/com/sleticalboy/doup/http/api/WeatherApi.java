package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.model.weather.City;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.Province;
import com.sleticalboy.doup.model.weather.WeatherBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.sleticalboy.doup.http.HttpConfig.HEADER_KEY;
import static com.sleticalboy.doup.http.HttpConfig.HEADER_VALUE_WEATHER;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public interface WeatherApi {

    String CUSTOM_HEADER = HEADER_KEY + ":" + HEADER_VALUE_WEATHER;

    /**
     * 获取省份信息
     */
    @Headers({CUSTOM_HEADER})
    @GET("china")
    Observable<List<Province>> getProvinces();

    /**
     * 获取市级信息
     *
     * @param provinceId 省份 id
     */
    @Headers({CUSTOM_HEADER})
    @GET("china/{provinceId}")
    Observable<List<City>> getCities(@Path("provinceId") int provinceId);

    /**
     * 获取区域信息
     *
     * @param provinceId 省份 id
     * @param cityCode   城市 code
     */
    @Headers({CUSTOM_HEADER})
    @GET("china/{provinceId}/{cityCode}")
    Observable<List<County>> getCounties(@Path("provinceId") int provinceId,
                                         @Path("cityCode") int cityCode);

    /**
     * 获取天气信息
     *
     * @param weatherId 具体区域对应的 weatherId
     * @param key       appkey
     */
    @Headers({CUSTOM_HEADER})
    @GET("weather")
    Observable<WeatherBean> getWeather(@Query("cityid") String weatherId,
                                       @Query("key") String key);
}
