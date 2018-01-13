package com.sleticalboy.doup.module.weather.dialog;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.IBaseView;
import com.sleticalboy.doup.model.WeatherModel;
import com.sleticalboy.doup.model.weather.City;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.Province;
import com.sleticalboy.doup.module.weather.WeatherActivity;
import com.sleticalboy.util.RxBus;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class DistrictDialog extends DialogFragment implements IBaseView, AdapterView.OnItemSelectedListener {

    public static final String TAG = "DistrictDialog";

    public static final int LEVEL_PROVINCE = 100;
    public static final int LEVEL_CITY = 101;
    public static final int LEVEL_COUNTY = 102;

    @BindView(R.id.title_area)
    TextView titleArea;
    @BindView(R.id.spinner_province)
    Spinner spinnerProvince;
    @BindView(R.id.spinner_city)
    Spinner spinnerCity;
    @BindView(R.id.spinner_county)
    Spinner spinnerCounty;

    Unbinder unbinder;

    private int mSelectedLevel = LEVEL_PROVINCE;

    private ProgressDialog mProgressDialog;
//    private DistrictPresenter mPresenter;

    private WeatherModel mWeatherModel;
    private boolean mIsFirst = true;

    private List<Province> mProvinceList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private List<County> mCountyList = new ArrayList<>();

    private final List<String> mProvinceNameList = new ArrayList<>();
    private final List<String> mCityNameList = new ArrayList<>();
    private final List<String> mCountyNameList = new ArrayList<>();
    private ArrayAdapter<String> mProvinceAdapter;
    private ArrayAdapter<String> mCityAdapter;
    private ArrayAdapter<String> mCountyAdapter;
    private ArrayAdapter<String> mEmptyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter = new DistrictPresenter(getActivity(), this);
        mWeatherModel = ((WeatherActivity) getActivity()).getWeatherModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 隐藏 dialog 默认标题
        Log.d(TAG, "getDialog():" + getDialog());
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 自定义 Dialog 位置
        final Window window = getDialog().getWindow();
        View dialogView = inflater.inflate(R.layout.dialog_choose_area,
                window.findViewById(android.R.id.content), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        unbinder = ButterKnife.bind(this, dialogView);

        initView();

        return dialogView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchProvince();
    }

    private void initView() {
        spinnerProvince.setOnItemSelectedListener(this);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerCounty.setOnItemSelectedListener(this);

        mProvinceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        mProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(mProvinceAdapter);

        mCityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(mCityAdapter);

        mCountyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        mCountyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCounty.setAdapter(mCountyAdapter);
    }

    /**
     * 刷新适配器并重置当前显示级别
     *
     * @param level 级别
     */
    public void updateDataSet(int level) {
        switch (level) {
            case LEVEL_PROVINCE:
                mSelectedLevel = LEVEL_PROVINCE;
                setupSpinnerView(mProvinceNameList);
                break;
            case LEVEL_CITY:
                mSelectedLevel = LEVEL_CITY;
                setupSpinnerView(mCityNameList);
                break;
            case LEVEL_COUNTY:
                mSelectedLevel = LEVEL_COUNTY;
                setupSpinnerView(mCountyNameList);
                break;
        }
    }

    private void setupSpinnerView(List<String> subDistrictNameList) {
        if (subDistrictNameList != null && subDistrictNameList.size() > 0) {
            if (mSelectedLevel == LEVEL_PROVINCE) {
                mProvinceAdapter.clear();
                mProvinceAdapter.addAll(subDistrictNameList);
                spinnerProvince.setSelection(0, true);
                mProvinceAdapter.notifyDataSetChanged();
            }
            if (mSelectedLevel == LEVEL_CITY) {
                mCityAdapter.clear();
                mCityAdapter.addAll(subDistrictNameList);
                spinnerCity.setSelection(0, true);
                mCityAdapter.notifyDataSetChanged();
            }
            if (mSelectedLevel == LEVEL_COUNTY) {
                mCountyAdapter.clear();
                mCountyAdapter.addAll(subDistrictNameList);
                spinnerCounty.setSelection(0, true);
                mCountyAdapter.notifyDataSetChanged();
            }
        } else {
            if (mEmptyAdapter == null)
                mEmptyAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, Collections.emptyList());
            if (mSelectedLevel == LEVEL_PROVINCE) {
                spinnerProvince.setAdapter(mEmptyAdapter);
                spinnerCity.setAdapter(mEmptyAdapter);
                spinnerCounty.setAdapter(mEmptyAdapter);
            }
            if (mSelectedLevel == LEVEL_CITY) {
                spinnerCity.setAdapter(mEmptyAdapter);
                spinnerCounty.setAdapter(mEmptyAdapter);
            }
            if (mSelectedLevel == LEVEL_COUNTY) {
                spinnerCounty.setAdapter(mEmptyAdapter);
            }
            mEmptyAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取省份数据
     */
    public void fetchProvince() {
        // 从数据库查询
        mProvinceList = DataSupport.findAll(Province.class);
        onLoading();
        if (mProvinceList != null && mProvinceList.size() > 0) { // 数据库中有数据
            Log.d(TAG, "从数据库获取省份数据");
            if (mProvinceNameList.size() == 0) {
                for (Province province : mProvinceList) {
                    mProvinceNameList.add(province.name);
                }
            }
            if (mIsFirst) {
                Province province = mProvinceList.get(0);
                fetchCity(province.id, province.provinceCode);
            }
            updateDataSet(DistrictDialog.LEVEL_PROVINCE);
            onLoadingEnd();
        } else { // 数据库中没有数据，从网络获取并存储数据库
            Log.d(TAG, "从网络获取省份数据");
            mWeatherModel.getProvinces()
                    .subscribeOn(Schedulers.io())
                    .filter(provinces -> provinces != null && provinces.size() > 0)
                    .doOnNext(provinces -> {
                        // 将数据保存到数据库中
                        for (Province province : provinces) {
                            province.provinceCode = province.id;
                            province.save();
                            mProvinceNameList.add(province.name);
                        }
                    })
                    .doOnNext(provinces -> {
                        if (mIsFirst) {
                            fetchCity(provinces.get(0).id, provinces.get(0).provinceCode);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provinces -> {
                        mProvinceList = provinces;
                        updateDataSet(DistrictDialog.LEVEL_PROVINCE);
                        onLoadingEnd();
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
        onLoading();
        mCityList = DataSupport.where("provinceId = ?", String.valueOf(provinceId)).find(City.class);
        if (mCityList != null && mCityList.size() > 0) {
            Log.d(TAG, "从数据库获取城市数据");
            mCityNameList.clear();
            for (City city : mCityList) {
                mCityNameList.add(city.name);
            }
            Log.d(TAG, "mCityNameList.size():" + mCityNameList.size());
            onLoadingEnd();
            updateDataSet(DistrictDialog.LEVEL_CITY);
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
                            city.save();
                            mCityNameList.add(city.name);
                        }
                    })
                    .doOnNext(cities -> {
                        if (mIsFirst) {
                            mIsFirst = false;
                            fetchCounty(cities.get(0).provinceId, cities.get(0).cityCode);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mCityList = cities;
                        updateDataSet(DistrictDialog.LEVEL_CITY);
                        onLoadingEnd();
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
        onLoading();
        mCountyList = DataSupport.where("cityId = ?", String.valueOf(cityCode)).find(County.class);
        if (mCountyList != null && mCountyList.size() > 0) {
            Log.d(TAG, "从数据库获取地区数据");
            for (County county : mCountyList) {
                mCountyNameList.add(county.name);
            }
            Log.d(TAG, "mCountyNameList.size():" + mCountyNameList.size());
            onLoadingEnd();
            updateDataSet(DistrictDialog.LEVEL_COUNTY);
        } else {
            Log.d(TAG, "从网络获取地区数据");
            mWeatherModel.getCounties(provinceId, cityCode)
                    .subscribeOn(Schedulers.io())
                    .filter(counties -> counties != null && counties.size() > 0)
                    .doOnNext(counties -> {
                        mCountyNameList.clear();
                        for (County county : counties) {
                            county.cityId = cityCode;
                            county.save();
                            mCountyNameList.add(county.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(counties -> {
                        mCountyList = counties;
                        updateDataSet(DistrictDialog.LEVEL_COUNTY);
                        onLoadingEnd();
                    });
        }
    }

    @Override
    public void onLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void onLoadingEnd() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_province:
                // 1,更新当前选中级别为 province
                // 2,获取该省份的所有城市数据
                // 3,获取该省份下第一个城市的所有地区数据
                mSelectedLevel = LEVEL_PROVINCE;
                Province selectedProvince = mProvinceList.get(position);
                fetchCity(selectedProvince.id, selectedProvince.provinceCode);
//                City firstCity = mCityList.get(0);
//                fetchCounty(firstCity.provinceId, firstCity.cityCode);
                break;
            case R.id.spinner_city:
                // 1,更新当前选中级别为 city
                // 2,获取该城市下的所有地区数据
                mSelectedLevel = LEVEL_CITY;
                City selectedCity = mCityList.get(position);
                Log.d(TAG, selectedCity.name);
                fetchCounty(selectedCity.provinceId, selectedCity.cityCode);
                break;
            case R.id.spinner_county:
                // 1,更新当前选中级别为 county
                // 2,跳转到天气信息页面，显示天气
                mSelectedLevel = LEVEL_COUNTY;
                // FIXME: 1/12/18 显示天气信息
                County selectedCounty = mCountyList.get(position);
                Log.d(TAG, "county:" + selectedCounty);
                RxBus.getBus().post(DistrictDialog.TAG, selectedCounty);
                dismiss();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                dismiss();
                break;
        }
    }
}
