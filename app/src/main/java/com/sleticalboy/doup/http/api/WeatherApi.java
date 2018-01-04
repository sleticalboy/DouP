package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.mvp.model.bean.weather.City;
import com.sleticalboy.doup.mvp.model.bean.weather.County;
import com.sleticalboy.doup.mvp.model.bean.weather.Province;
import com.sleticalboy.doup.mvp.model.bean.weather.WeatherBean;

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

    /**
     * 获取省份信息
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_WEATHER})
    @GET("china")
    Observable<List<Province>> getProvinces();

    /**
     * 获取市级信息
     *
     * @param provinceId 省份 id
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_WEATHER})
    @GET("china/{provinceId}")
    Observable<List<City>> getCities(@Path("provinceId") int provinceId);

    /**
     * 获取区域信息
     *
     * @param provinceId 省份 id
     * @param cityCode     城市 id
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_WEATHER})
    @GET("china/{provinceId}/{cityCode}")
    Observable<List<County>> getCounties(@Path("provinceId") int provinceId,
                                         @Path("cityCode") int cityCode);

    /**
     * 获取天气信息
     *
     * @param weatherId 具体区域对应的天气 id
     * @param key       appkey
     */
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_WEATHER})
    @GET("weather")
    Observable<WeatherBean> getWeather(@Query("cityid") String weatherId,
                                       @Query("key") String key);

    @Deprecated
    @Headers({HEADER_KEY + ":" + HEADER_VALUE_WEATHER})
    @GET("weather?cityid=CN101050109&key=f528b9b4264e4f9b977645d60f321a0c")
    Observable<WeatherBean> getWeather();

}