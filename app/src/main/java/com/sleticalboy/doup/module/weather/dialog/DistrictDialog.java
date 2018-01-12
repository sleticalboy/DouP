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

    private List<Province> mProvinceList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private List<County> mCountyList = new ArrayList<>();

    private List<String> mProvinceNameList = new ArrayList<>();
    private List<String> mCityNameList = new ArrayList<>();
    private List<String> mCountyNameList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

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
    }

    /**
     * 刷新适配器并重置当前显示级别
     *
     * @param level 级别
     */
    public void updateDataSet(int level) {
        switch (level) {
            case LEVEL_PROVINCE:
                setupSpinnerView(mProvinceNameList);
                break;
            case LEVEL_CITY:
                setupSpinnerView(mCityNameList);
                break;
            case LEVEL_COUNTY:
                setupSpinnerView(mCountyNameList);
                break;
        }
    }

    private void setupSpinnerView(List<String> subDistrictNameList) {
        if (subDistrictNameList != null && subDistrictNameList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                        subDistrictNameList);
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
            if (mSelectedLevel == LEVEL_PROVINCE)
                spinnerProvince.setAdapter(mAdapter);
            if (mSelectedLevel == LEVEL_CITY)
                spinnerCity.setAdapter(mAdapter);
            if (mSelectedLevel == LEVEL_COUNTY)
                spinnerCounty.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, Collections.emptyList());
            if (mSelectedLevel == LEVEL_PROVINCE) {
                spinnerProvince.setAdapter(emptyAdapter);
                spinnerCity.setAdapter(emptyAdapter);
                spinnerCounty.setAdapter(emptyAdapter);
            }
            if (mSelectedLevel == LEVEL_CITY) {
                spinnerCity.setAdapter(emptyAdapter);
                spinnerCounty.setAdapter(emptyAdapter);
            }
            if (mSelectedLevel == LEVEL_COUNTY)
                spinnerCounty.setAdapter(emptyAdapter);
            emptyAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取省份数据，先从数据库查询，若不存在再从网络获取之后保存到数据库
     */
    public void fetchProvince() {
        mProvinceList = DataSupport.findAll(Province.class);
        onLoading();
        if (mProvinceList != null && mProvinceList.size() > 0) { // 数据库中有数据
            Log.d(TAG, "从数据库获取省份数据");
            Log.d(TAG, "mProvinceList.size():" + mProvinceList.size());
            if (mProvinceNameList.size() == 0) {
                for (Province province : mProvinceList) {
                    mProvinceNameList.add(province.name);
                }
            }
            onLoadingEnd();
        } else { // 数据库中没有数据，从网络获取并存储数据库
            Log.d(TAG, "从网络获取省份数据");
            mWeatherModel.getProvinces()
                    .subscribeOn(Schedulers.io())
                    .filter(provinces -> provinces != null && provinces.size() > 0)
                    .doOnNext(provinces -> {
                        for (Province province : provinces) {
                            province.provinceCode = province.id;
                            province.save();
                            mProvinceNameList.add(province.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provinces -> {
                        mProvinceList = provinces;
//                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_PROVINCE);
                        updateDataSet(DistrictDialog.LEVEL_PROVINCE);
                        onLoadingEnd();
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
//            mDistrictView.updateDataSet(DistrictDialog.LEVEL_CITY);
            updateDataSet(DistrictDialog.LEVEL_CITY);
        } else {
            Log.d(TAG, "从网络获取城市数据");
            onLoading();
            mWeatherModel.getCities(province.id)
                    .subscribeOn(Schedulers.io())
                    .filter(cities -> cities != null && cities.size() > 0)
                    .doOnNext(cities -> {
                        for (City city : cities) {
                            city.provinceId = province.provinceCode;
                            city.cityCode = city.id;
                            city.save();
                            mCityNameList.add(city.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mCityList = cities;
//                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_CITY);
                        updateDataSet(DistrictDialog.LEVEL_CITY);
                        onLoadingEnd();
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
//            mDistrictView.updateDataSet(DistrictDialog.LEVEL_COUNTY);
            updateDataSet(DistrictDialog.LEVEL_COUNTY);
        } else {
            Log.d(TAG, "从网络获取地区数据");
            onLoading();
            mWeatherModel.getCounties(city.provinceId, city.cityCode)
                    .subscribeOn(Schedulers.io())
                    .filter(counties -> counties != null && counties.size() > 0)
                    .doOnNext(counties -> {
                        for (County county : counties) {
                            county.cityId = city.cityCode;
                            county.save();
                            mCountyNameList.add(county.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(counties -> {
                        mCountyList = counties;
//                        mDistrictView.updateDataSet(DistrictDialog.LEVEL_COUNTY);
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
                mSelectedLevel = LEVEL_PROVINCE;
                fetchCity(mProvinceList.get(position));
                break;
            case R.id.spinner_city:
                mSelectedLevel = LEVEL_CITY;
                fetchCounty(mCityList.get(position));
                break;
            case R.id.spinner_county:
                mSelectedLevel = LEVEL_COUNTY;
                // FIXME: 1/12/18 显示天气信息
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
