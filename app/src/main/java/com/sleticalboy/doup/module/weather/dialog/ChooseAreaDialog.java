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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.weather.WeatherActivity;
import com.sleticalboy.doup.model.WeatherModel;
import com.sleticalboy.doup.model.weather.City;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.Province;
import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.SPUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class ChooseAreaDialog extends DialogFragment {

    public static final String TAG = "ChooseAreaDialog";

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    @BindView(R.id.title_area)
    TextView titleArea;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.lv_area)
    ListView lvArea;
    Unbinder unbinder;

    private WeatherModel mWeatherModel;
    private ArrayAdapter<String> mAdapter;
    private List<String> mData = new ArrayList<>();
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;
    private Province mSelectedProvince;
    private City mSelectedCity;
    private int mCurrentLevel = LEVEL_PROVINCE;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Window window = getDialog().getWindow();
        assert window != null;

        View dialogView = inflater.inflate(R.layout.dialog_choose_area, window.findViewById(android.R.id.content), false);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        unbinder = ButterKnife.bind(this, dialogView);

        initView();

        return dialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Window window = getDialog().getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.0f;
        params.alpha = 0.9f;
        window.setAttributes(params);
    }

    private void initView() {
        btnBack.setVisibility(View.GONE);
        titleArea.setText(R.string.choose_area);
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mData);
        lvArea.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        lvArea.setOnItemClickListener((parent, view, position, id) -> {
            switch (mCurrentLevel) {
                case LEVEL_PROVINCE:
                    closeBackButton();
                    Province province = mProvinceList.get(position);
                    initCity(province);
                    break;
                case LEVEL_CITY:
                    showBackButton();
                    City city = mCityList.get(position);
                    initCounty(city);
                    break;
                case LEVEL_COUNTY:
                    showBackButton();
                    County county = mCountyList.get(position);
                    titleArea.setText(county.name);
                    Log.d(TAG, "post --> " + county.toString());
                    // 关闭地区选择 dialog, 发送消息到 weatherActivity 显示天气
                    SPUtils.putString(ConstantValue.KEY_AREA, county.name);
                    SPUtils.putString(ConstantValue.KEY_WEATHER_ID, county.weatherId);
                    RxBus.getBus().post(TAG, county);
                    dismiss();
                    break;
                default:
                    break;
            }
        });

        btnBack.setOnClickListener(v -> {
            switch (mCurrentLevel) {
                case LEVEL_CITY:
                    initProvince();
                    break;
                case LEVEL_COUNTY:
                    initCity(mSelectedProvince);
                    break;
                default:
                    break;
            }
        });
    }

    private void showBackButton() {
        btnBack.setVisibility(View.VISIBLE);
    }

    private void closeBackButton() {
        btnBack.setVisibility(View.GONE);
    }

    private void initData() {
        initProvince();
    }

    // 获取省份数据，先从数据库查询，若不存在再从网络获取之后保存到数据库
    private void initProvince() {
        titleArea.setText("中国");
        btnBack.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        mData.clear();
        if (mProvinceList != null && mProvinceList.size() > 0) { // 数据库中有数据
            Log.d(TAG, "从数据库获取省份数据");
            for (Province province : mProvinceList) {
                mData.add(province.name);
            }
            updateDataSet(LEVEL_PROVINCE);
        } else { // 数据库中没有数据，从网络获取并存储数据库
            showDialog();
            Log.d(TAG, "从网络获取省份数据");
            mWeatherModel.getProvinces()
                    .subscribeOn(Schedulers.io())
                    .filter(provinces -> provinces != null && provinces.size() > 0)
                    .doOnNext(provinces -> {
                        for (Province province : provinces) {
                            province.provinceCode = province.id;
                            province.save();
                            mData.add(province.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(provinces -> {
                        mProvinceList = provinces;
                        updateDataSet(LEVEL_PROVINCE);
                    });
        }
    }

    // 刷新适配器并重置当前显示级别
    private void updateDataSet(int level) {
        mAdapter.notifyDataSetChanged();
        lvArea.setSelection(0);
        mCurrentLevel = level;
        closeDialog();
    }

    // 获取城市数据，同获取省份数据
    private void initCity(Province province) {
        mSelectedProvince = province;
        titleArea.setText(province.name);
        Log.d(TAG, province.name);
        mCityList = DataSupport.where("provinceId = ?", String.valueOf(province.id)).find(City.class);
        mData.clear();
        if (mCityList != null && mCityList.size() > 0) {
            Log.d(TAG, "从数据库获取城市数据");
            for (City city : mCityList) {
                mData.add(city.name);
            }
            updateDataSet(LEVEL_CITY);
        } else {
            Log.d(TAG, "从网络获取城市数据");
            showDialog();
            mWeatherModel.getCities(province.id)
                    .subscribeOn(Schedulers.io())
                    .filter(cities -> cities != null && cities.size() > 0)
                    .doOnNext(cities -> {
                        for (City city : cities) {
                            city.provinceId = province.provinceCode;
                            city.cityCode = city.id;
                            city.save();
                            mData.add(city.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> {
                        mCityList = cities;
                        updateDataSet(LEVEL_CITY);
                    });
        }
    }

    // 获取地区数据，同获取省份数据
    private void initCounty(City city) {
        mSelectedCity = city;
        titleArea.setText(city.name);
        Log.d(TAG, city.name);
        mCountyList = DataSupport.where("cityId = ?", String.valueOf(city.cityCode)).find(County.class);
        mData.clear();
        if (mCountyList != null && mCountyList.size() > 0) {
            Log.d(TAG, "从数据库获取地区数据");
            for (County county : mCountyList) {
                mData.add(county.name);
            }
            updateDataSet(LEVEL_COUNTY);
        } else {
            Log.d(TAG, "从网络获取地区数据");
            showDialog();
            mWeatherModel.getCounties(city.provinceId, city.cityCode)
                    .subscribeOn(Schedulers.io())
                    .filter(counties -> counties != null && counties.size() > 0)
                    .doOnNext(counties -> {
                        for (County county : counties) {
                            county.cityId = city.cityCode;
                            county.save();
                            mData.add(county.name);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(counties -> {
                        mCountyList = counties;
                        updateDataSet(LEVEL_COUNTY);
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void closeDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
