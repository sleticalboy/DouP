package com.sleticalboy.doup.fragment.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.MainActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.weather.City;
import com.sleticalboy.doup.bean.weather.County;
import com.sleticalboy.doup.bean.weather.Province;
import com.sleticalboy.doup.bean.weather.WeatherBean;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.mvp.model.WeatherModel;
import com.sleticalboy.doup.util.ImageLoader;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class WeatherFragment extends Fragment {

    private static final String TAG = "WeatherFragment";
    @BindView(R.id.weather_content)
    TextView weatherContent;
    @BindView(R.id.img_bg)
    ImageView imgBg;

    private String mUrl;

    private WeatherModel mWeatherModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = View.inflate(getContext(), R.layout.frag_weather, null);
        ButterKnife.bind(this, rootView);

        mWeatherModel = new WeatherModel(getContext());

        initView();

        initData();

        return rootView;
    }

    private void initData() {

        HttpUtils.request(ApiConstant.BASE_WEATHER_URL + "bing_pic", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    mUrl = response.body().string();
                    getActivity().runOnUiThread(() -> ImageLoader.loadHigh(getContext(), imgBg, mUrl));
                }

            }
        });

        mWeatherModel.getWeather("CN101050109")
                .subscribe(weatherBean -> {
                    // TODO: 12/30/17 获取并展示天气数据
                    Log.d(TAG, "weather = " + weatherBean);
//                        mData = weatherBean;
//                        weatherContent.setText(weatherBean.HeWeather.get(0).toString());
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.w(TAG, throwable);
                    }
                });
    }

    private void initView() {
        MainActivity activity = (MainActivity) getActivity();
        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar != null) {
//            supportActionBar.hide();
            supportActionBar.setTitle(R.string.weather);
            supportActionBar.setHideOnContentScrollEnabled(false);
        }
    }

    private void initData2() {
        mWeatherModel.getProvinces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .forEach(province -> {
                    province.save();
                    initCity(province);
                });
    }

    private void initCity(Province province) {
        mWeatherModel.getCities(province.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .forEach(city -> {
                    city.save();
                    initCounty(city);
                });
    }

    private void initCounty(City city) {
        mWeatherModel.getCounties(city.provinceId, city.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .forEach(county -> {
                    county.save();
                    getWeather(county);
                });
    }

    private void getWeather(County county) {
        mWeatherModel.getWeather(county.weatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherBean>() {
                    @Override
                    public void call(WeatherBean weatherBean) {
                        Log.d("SplashActivity", "weatherBean:" + weatherBean);
                    }
                }, Throwable::printStackTrace);
    }
}
