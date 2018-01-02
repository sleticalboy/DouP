package com.sleticalboy.doup.http.api;

import com.sleticalboy.doup.bean.weather.City;
import com.sleticalboy.doup.bean.weather.County;
import com.sleticalboy.doup.bean.weather.Province;
import com.sleticalboy.doup.bean.weather.WeatherBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("china")
    rx.Observable<List<Province>> getProvinces();

    /**
     * 获取市级信息
     *
     * @param provinceId 省份 id
     */
    @GET("china/{provinceId}")
    rx.Observable<List<City>> getCities(@Path("id") int provinceId);

    /**
     * 获取区域信息
     *
     * @param provinceId 省份 id
     * @param cityId     城市 id
     */
    @GET("china/{provinceId}/{cityId}")
    rx.Observable<List<County>> getCounties(@Path("id") int provinceId,
                                            @Path("id") int cityId);

    /**
     * 获取天气信息
     *
     * @param weatherId 具体区域对应的天气 id
     * @param key       appkey
     */
    @GET("weather")
    rx.Observable<WeatherBean> getWeather(@Query("cityid") String weatherId,
                                          @Query("key") String key);

    @Deprecated
    @GET("weather?cityid=CN101050109&key=f528b9b4264e4f9b977645d60f321a0c")
    rx.Observable<WeatherBean> getWeather();

}
