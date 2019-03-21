package com.sleticalboy.doup.model.weather;

import android.content.Context;

import com.sleticalboy.base.BaseModel;
import com.sleticalboy.doup.bean.weather.City;
import com.sleticalboy.doup.bean.weather.County;
import com.sleticalboy.doup.bean.weather.Province;
import com.sleticalboy.doup.bean.weather.WeatherBean;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.WeatherApi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/1/18.
 *
 * @author sleticalboy
 */
public final class WeatherModel extends BaseModel {

    private static final String APP_KEY = "f528b9b4264e4f9b977645d60f321a0c";

    private WeatherApi mWeatherApiService;

    public WeatherModel(Context context) {
        super(context);
        // RetrofitClient client = RetrofitClient.retrofit(getWeakReference().get(), ApiConstant.BASE_WEATHER_URL);
        mWeatherApiService = RetrofitClient.retrofit().create(WeatherApi.class);
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

    @Override
    public void clear() {
        super.clear();
        if (mWeatherApiService != null) {
            mWeatherApiService = null;
        }
    }
}
