package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.WeatherApi;
import com.sleticalboy.doup.mvp.model.bean.weather.City;
import com.sleticalboy.doup.mvp.model.bean.weather.County;
import com.sleticalboy.doup.mvp.model.bean.weather.Province;
import com.sleticalboy.doup.mvp.model.bean.weather.WeatherBean;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/1/18.
 *
 * @author sleticalboy
 */
public class WeatherModel {

    private static final String APP_KEY = "f528b9b4264e4f9b977645d60f321a0c";

    private WeakReference<Context> mWeakReference;
    private WeatherApi mWeatherApiService;

    public WeatherModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_WEATHER_URL);
        mWeatherApiService = client.create(WeatherApi.class);
    }

    public Observable<List<Province>> getProvinces() {
        return mWeatherApiService.getProvinces();
    }

    public Observable<List<City>> getCities(int provinceId) {
        return mWeatherApiService.getCities(provinceId);
    }

    public Observable<List<County>> getCounties(int provinceId, int cityCode) {
        return mWeatherApiService.getCounties(provinceId, cityCode);
    }

    public Observable<WeatherBean> getWeather(String weatherId) {
        return mWeatherApiService.getWeather(weatherId, APP_KEY);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
