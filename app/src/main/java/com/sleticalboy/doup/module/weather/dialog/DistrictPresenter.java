package com.sleticalboy.doup.module.weather.dialog;

import android.content.Context;
import android.util.Log;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.model.WeatherModel;
import com.sleticalboy.doup.model.weather.City;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.Province;
import com.sleticalboy.doup.module.weather.WeatherActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/12/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class DistrictPresenter extends BasePresenter {

    public static final String TAG = "DistrictPresenter";

    private DistrictDialog mDistrictView;
    private WeatherModel mWeatherModel;

    private List<Province> mProvinceList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private List<County> mCountyList = new ArrayList<>();

    public DistrictPresenter(Context context, DistrictDialog districtView) {
        super(context);
        mDistrictView = districtView;
        mWeatherModel = ((WeatherActivity) districtView.getActivity()).getWeatherModel();
    }

    /**
     * 获取省份数据，先从数据库查询，若不存在再从网络获取之后保存到数据库
     */
    public void fetchProvince() {
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList != null && mProvinceList.size() > 0) { // 数据库中有数据
            Log.d(TAG, "从数据库获取省份数据");
            mDistrictView.updateDataSet(DistrictDialog.LEVEL_PROVINCE);
        } else { // 数据库中没有数据，从网络获取并存储数据库
            mDistrictView.onLoading();
            Log.d(TAG, "从网络获取省份数据");
            mWeatherModel.getProvinces()
                    .subscribeOn(Schedulers.io())
                    .filter(provinces -> provinces != null && provinces.size() > 0)
                    .doOnNext(provinces -> {
                        for (Province province : provinces) {
                            province.provinceCode = province.id;
                            province.save();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provinces -> {
                        mProvinceList = provinces;
                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_PROVINCE);
                    });
        }
    }

    /**
     * 获取某一省份下的所有城市数据
     *
     * @param province Province 对象
     */
    public void fetchCity(Province province) {
        Log.d(TAG, province.name);
        mCityList = DataSupport.where("provinceId = ?", String.valueOf(province.id)).find(City.class);
        if (mCityList != null && mCityList.size() > 0) {
            Log.d(TAG, "从数据库获取城市数据");
            mDistrictView.updateDataSet(DistrictDialog.LEVEL_CITY);
        } else {
            Log.d(TAG, "从网络获取城市数据");
            mDistrictView.onLoading();
            mWeatherModel.getCities(province.id)
                    .subscribeOn(Schedulers.io())
                    .filter(cities -> cities != null && cities.size() > 0)
                    .doOnNext(cities -> {
                        for (City city : cities) {
                            city.provinceId = province.provinceCode;
                            city.cityCode = city.id;
                            city.save();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mCityList = cities;
                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_CITY);
                    });
        }
    }

    /**
     * 获取某以城市下所有区数据
     *
     * @param city City 对象
     */
    public void fetchCounty(City city) {
        Log.d(TAG, city.name);
        mCountyList = DataSupport.where("cityId = ?", String.valueOf(city.cityCode)).find(County.class);
        if (mCountyList != null && mCountyList.size() > 0) {
            Log.d(TAG, "从数据库获取地区数据");
            mDistrictView.updateDataSet(DistrictDialog.LEVEL_COUNTY);
        } else {
            Log.d(TAG, "从网络获取地区数据");
            mDistrictView.onLoading();
            mWeatherModel.getCounties(city.provinceId, city.cityCode)
                    .subscribeOn(Schedulers.io())
                    .filter(counties -> counties != null && counties.size() > 0)
                    .doOnNext(counties -> {
                        for (County county : counties) {
                            county.cityId = city.cityCode;
                            county.save();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(counties -> {
                        mCountyList = counties;
                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_COUNTY);
                    });
        }
    }
}
