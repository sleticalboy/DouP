package com.sleticalboy.doup.module.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.model.weather.WeatherModel;
import com.sleticalboy.doup.model.weather.City;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.Province;
import com.sleticalboy.doup.module.weather.service.AutoUpdateService;
import com.sleticalboy.util.SPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class WeatherPresenter extends BasePresenter implements IWeatherContract.IWeatherPresenter {

    public static final String TAG = "WeatherPresenter";

    private WeatherActivity mWeatherView;
    private WeatherModel mWeatherModel;

    private List<Province> mProvinceList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private List<County> mCountyList = new ArrayList<>();

    private final List<String> mProvinceNameList = new ArrayList<>();
    private final List<String> mCityNameList = new ArrayList<>();
    private final List<String> mCountyNameList = new ArrayList<>();

    public WeatherPresenter(Context context, WeatherActivity weatherView) {
        super(context);
        mWeatherView = weatherView;
        mWeatherModel = new WeatherModel(context);
    }

    /**
     * 获取省份数据
     */
    public void fetchProvince() {
        // 从数据库查询
//        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList != null && mProvinceList.size() > 0) { // 数据库中有数据
            Log.d(TAG, "从数据库获取省份数据");
            if (mProvinceNameList.size() == 0) {
                for (Province province : mProvinceList) {
                    mProvinceNameList.add(province.name);
                }
            }
            Province province = mProvinceList.get(0);
            fetchCity(province.id, province.provinceCode);
        } else { // 数据库中没有数据，从网络获取并存储数据库
            Log.d(TAG, "从网络获取省份数据");
            mWeatherModel.getProvinces()
                    .subscribeOn(Schedulers.io())
                    .filter(provinces -> provinces != null && provinces.size() > 0)
                    .doOnNext(provinces -> {
                        // 将数据保存到数据库中
                        for (Province province : provinces) {
                            province.provinceCode = province.id;
//                            province.save();
                            mProvinceNameList.add(province.name);
                        }
                    })
                    .doOnNext(provinces -> {
                        fetchCity(provinces.get(0).id, provinces.get(0).provinceCode);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provinces -> {
                        mProvinceList = provinces;
                    });
        }
    }

    /**
     * 获取某一省份下的所有城市数据
     *
     * @param provinceId   province id
     * @param provinceCode province code
     */
    public void fetchCity(int provinceId, int provinceCode) {
//        mCityList = DataSupport.where("provinceId = ?", String.valueOf(provinceId)).find(City.class);
        if (mCityList != null && mCityList.size() > 0) {
            Log.d(TAG, "从数据库获取城市数据");
            mCityNameList.clear();
            for (City city : mCityList) {
                mCityNameList.add(city.name);
            }
        } else {
            Log.d(TAG, "从网络获取城市数据");
            mWeatherModel.getCities(provinceId)
                    .subscribeOn(Schedulers.io())
                    .filter(cities -> cities != null && cities.size() > 0)
                    .doOnNext(cities -> {
                        // 将数据保存到数据库中
                        mCityNameList.clear();
                        for (City city : cities) {
                            city.provinceId = provinceCode;
                            city.cityCode = city.id;
//                            city.save();
                            mCityNameList.add(city.name);
                        }
                    })
                    .doOnNext(cities -> {
                        fetchCounty(cities.get(0).provinceId, cities.get(0).cityCode);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mCityList = cities;
                    });
        }
    }

    /**
     * 获取某以城市下所有区数据
     *
     * @param provinceId province id
     * @param cityCode   city code
     */
    public void fetchCounty(int provinceId, int cityCode) {
//        mCountyList = DataSupport.where("cityId = ?", String.valueOf(cityCode)).find(County.class);
        if (mCountyList != null && mCountyList.size() > 0) {
            Log.d(TAG, "从数据库获取地区数据");
            for (County county : mCountyList) {
                mCountyNameList.add(county.name);
            }
            Log.d(TAG, "mCountyNameList.size():" + mCountyNameList.size());
        } else {
            Log.d(TAG, "从网络获取地区数据");
            mWeatherModel.getCounties(provinceId, cityCode)
                    .subscribeOn(Schedulers.io())
                    .filter(counties -> counties != null && counties.size() > 0)
                    .doOnNext(counties -> {
                        mCountyNameList.clear();
                        for (County county : counties) {
                            county.cityId = cityCode;
//                            county.save();
                            mCountyNameList.add(county.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(counties -> {
                        mCountyList = counties;
                    });
        }
    }

    /**
     * 获取天气数据
     *
     * @param weatherId 天气 id
     */
    public void getWeather(String weatherId) {
        if (!TextUtils.isEmpty(weatherId)) {
//            throw new IllegalArgumentException("weatherId is null");
            mWeatherModel.getWeather(weatherId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherBean -> {
                        Log.d("WeatherActivity", "get weather form network -- >");
                        SPUtils.INSTANCE.putString(ConstantValue.Companion.getKEY_WEATHER(), new Gson().toJson(weatherBean));
                        mWeatherView.showWeather(weatherBean);
                        mWeatherView.onLoadFinished();
                        AutoUpdateService.actionStart(getContext());
                    }, throwable -> mWeatherView.onNetError());
        }
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
                    SPUtils.INSTANCE.putString(ConstantValue.Companion.getKEY_BG(), url);
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
