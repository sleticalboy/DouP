package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.bean.weather.City;
import com.sleticalboy.doup.bean.weather.County;
import com.sleticalboy.doup.bean.weather.Province;
import com.sleticalboy.doup.bean.weather.WeatherBean;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.WeatherApi;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 1/1/18.
 *
 * @author sleticalboy
 */
public class WeatherModel {

    public static final String APP_KEY = "f528b9b4264e4f9b977645d60f321a0c";

    private WeakReference<Context> mWeakReference;
    private WeatherApi mWeatherApiService;

    public WeatherModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_WEATHER_URL);
        mWeatherApiService = client.create(WeatherApi.class);
    }

    public rx.Observable<List<Province>> getProvinces() {
        return mWeatherApiService.getProvinces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<List<City>> getCities(int provinceId) {
        return mWeatherApiService.getCities(provinceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<List<County>> getCounties(int provinceId, int cityId) {
        return mWeatherApiService.getCounties(provinceId, cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<WeatherBean> getWeather(String weatherId) {
        return mWeatherApiService.getWeather(weatherId, APP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
