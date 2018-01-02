package com.sleticalboy.doup.fragment.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.mvp.model.WeatherModel;
import com.sleticalboy.doup.mvp.model.bean.weather.City;
import com.sleticalboy.doup.mvp.model.bean.weather.Province;
import com.sleticalboy.doup.mvp.model.bean.weather.WeatherBean;
import com.sleticalboy.doup.util.ImageLoader;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;

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
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private Unbinder unbind;

    private String mUrl;
    private WeatherBean mData;

    private WeatherModel mWeatherModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = View.inflate(getContext(), R.layout.frag_weather, null);
        unbind = ButterKnife.bind(this, rootView);

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
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    mUrl = responseBody.string();
                    getActivity().runOnUiThread(() -> ImageLoader.loadHigh(getContext(), imgBg, mUrl));
                }
            }
        });

        getWeather("CN101050109");
    }

    private void initView() {

        setupSwipeRefreshLayout();
    }

    private void setupSwipeRefreshLayout() {
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
        }
    }

    private void initCity(Province province) {
        mWeatherModel.getCities(province.id)
                .flatMap(Observable::from)
                .forEach(city -> {
                    city.save();
                    initCounty(city);
                });
    }

    private void initCounty(City city) {
        mWeatherModel.getCounties(city.provinceId, city.id)
                .flatMap(Observable::from)
                .forEach(county -> {
                    county.save();
                    getWeather(county.weatherId);
                });
    }

    private void getWeather(String weatherId) {
        mWeatherModel.getWeather(weatherId)
                .subscribe(weatherBean -> {
                    // TODO: 12/30/17 获取并展示天气数据
                    Log.d("SplashActivity", "weatherBean:" + weatherBean);
                    Log.d(TAG, "weather = " + weatherBean);
                    mData = weatherBean;
                    weatherContent.setText(weatherBean.HeWeather.get(0).toString());
                }, Throwable::printStackTrace);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWeatherModel.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
