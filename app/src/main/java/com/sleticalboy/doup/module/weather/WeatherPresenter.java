package com.sleticalboy.doup.module.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.model.WeatherModel;
import com.sleticalboy.doup.module.weather.service.AutoUpdateService;
import com.sleticalboy.util.SPUtils;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/12/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class WeatherPresenter extends BasePresenter {

    private WeatherActivity mWeatherView;
    private WeatherModel mWeatherModel;

    public WeatherPresenter(Context context, WeatherActivity weatherView) {
        super(context);
        mWeatherView = weatherView;
        mWeatherModel = new WeatherModel(context);
    }

    public WeatherModel getWeatherModel() {
        return mWeatherModel;
    }

    /**
     * 获取天气数据
     *
     * @param weatherId 天气 id
     */
    public void getWeather(String weatherId) {
        if (TextUtils.isEmpty(weatherId))
            throw new IllegalArgumentException("weatherId is null");

        mWeatherModel.getWeather(weatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherBean -> {
                    Log.d("WeatherActivity", "get weather form network -- >");
                    SPUtils.putString(ConstantValue.KEY_WEATHER, new Gson().toJson(weatherBean));
                    mWeatherView.showWeather(weatherBean);
                    mWeatherView.onLoadingEnd();
                    AutoUpdateService.actionStart(getContext());
                }, throwable -> mWeatherView.onNetError());
    }

    /**
     * 获取背景图片 url
     */
    public void getBgUrl() {
        HttpUtils.request(ApiConstant.BASE_WEATHER_URL + "bing_pic", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mWeatherView.onNetError();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String url = responseBody.string();
                    SPUtils.putString(ConstantValue.KEY_BG, url);
                    mWeatherView.showBg(url);
                }
            }
        });
    }

    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mWeatherModel.clear();
        mWeatherView = null;
    }
}
