package com.sleticalboy.doup.module.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.module.weather.dialog.ChooseAreaDialog;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.model.WeatherModel;
import com.sleticalboy.doup.model.weather.County;
import com.sleticalboy.doup.model.weather.WeatherBean;
import com.sleticalboy.doup.module.weather.service.AutoUpdateService;
import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.SPUtils;
import com.sleticalboy.util.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public class WeatherActivity extends BaseActivity {

    private static final String TAG = "WeatherActivity";
    public static final String DIALOG_TAG = "ChooseAreaDialog";

    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.btn_navigation)
    Button btnNavigation;
    @BindView(R.id.title_city)
    TextView titleCity;

    @BindView(R.id.title_update_time)
    TextView titleUpdateTime;
    @BindView(R.id.tv_degree)
    TextView tvDegree;
    @BindView(R.id.tv_weather_info)
    TextView tvWeatherInfo;
    @BindView(R.id.layout_forecast)
    LinearLayout layoutForecast;
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    @BindView(R.id.tv_pm25)
    TextView tvPm25;
    @BindView(R.id.tv_comfort)
    TextView tvComfort;
    @BindView(R.id.tv_car_wash)
    TextView tvCarWash;
    @BindView(R.id.tv_sport)
    TextView tvSport;
    @BindView(R.id.layout_weather)
    ScrollView layoutWeather;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private WeatherModel mWeatherModel;
    private Observable<County> mObservable;
    private String mWeatherId = SPUtils.getString(ConstantValue.KEY_WEATHER_ID, null);

    @Override
    protected void initData() {
        mWeatherModel = new WeatherModel(this);

        HttpUtils.request(ApiConstant.BASE_WEATHER_URL + "bing_pic", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ToastUtils.showToast(WeatherActivity.this, "网络错误");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String url = responseBody.string();
                    SPUtils.putString(ConstantValue.KEY_BG, url);
                    runOnUiThread(() -> ImageLoader.loadHigh(WeatherActivity.this, imgBg, url));
                }
            }
        });

        mObservable.subscribe(county -> {
            if (TextUtils.isEmpty(mWeatherId))
                mWeatherId = county.weatherId;
            Log.d(TAG, mWeatherId);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(county.name);
            }
            swipeRefresh.setRefreshing(true);
            Log.d("WeatherActivity", "form dialog");
            getWeather(county.weatherId);
        });
    }

    public WeatherModel getWeatherModel() {
        return mWeatherModel;
    }

    // 获取天气信息
    public void getWeather(String weatherId) {
        if (TextUtils.isEmpty(weatherId))
            throw new IllegalArgumentException("weatherId is null");

        mWeatherModel.getWeather(weatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherBean -> {
                    Log.d("WeatherActivity", "get weather form network -- >");
                    SPUtils.putString(ConstantValue.KEY_WEATHER, new Gson().toJson(weatherBean));
                    showWeather(weatherBean);
                    swipeRefresh.setRefreshing(false);
                }, throwable -> {
                    // 获取天气信息失败
                    ToastUtils.showToast(WeatherActivity.this, "获取天气信息失败");
                    swipeRefresh.setRefreshing(false);
                });
    }

    // 展示天气信息
    private void showWeather(WeatherBean weatherBean) {
        WeatherBean.HeWeatherBean weather = weatherBean.HeWeather.get(0);

        titleCity.setText(weather.basic.city);
        String updateTime = weather.basic.update.loc.split(" ")[1];
        titleUpdateTime.setText(updateTime);
        String degree = weather.now.tmp + "°C";
        tvDegree.setText(degree);
        tvWeatherInfo.setText(String.format("%s / %s", weather.now.wind.dir, weather.now.wind.sc));

        layoutForecast.removeAllViews();
        int size = weather.dailyForecast.size();
        for (int i = 0; i < size; i++) {
            WeatherBean.HeWeatherBean.DailyForecastBean forecast = weather.dailyForecast.get(i);

            View view = View.inflate(this, R.layout.item_forecast, null);
            TextView tvDate = view.findViewById(R.id.tv_date);
            TextView tvInfo = view.findViewById(R.id.tv_info);
            TextView tvMax = view.findViewById(R.id.tv_max);
            TextView tvMin = view.findViewById(R.id.tv_min);

            tvDate.setText(forecast.date);
            tvInfo.setText(String.format("%s / %s", forecast.wind.dir, forecast.wind.sc));
            tvMax.setText(forecast.tmp.max);
            tvMin.setText(forecast.tmp.min);

            layoutForecast.addView(view);
        }

        if (weather.aqi != null) {
            tvAqi.setText(weather.aqi.city.aqi);
            tvPm25.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.comf.txt;
        String carWash = "洗车指数：" + weather.suggestion.cw.txt;
        String sport = "运动建议：" + weather.suggestion.sport.txt;
        tvComfort.setText(comfort);
        tvCarWash.setText(carWash);
        tvSport.setText(sport);

        layoutWeather.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    @Override
    protected void initView() {

        ChooseAreaDialog dialog = new ChooseAreaDialog();
        String area = SPUtils.getString(ConstantValue.KEY_AREA, null);
        if (TextUtils.isEmpty(area))
            showDialog(dialog);
        else
            titleCity.setText(area);
        String weatherStr = SPUtils.getString(ConstantValue.KEY_WEATHER, null);
        if (weatherStr != null) {
            WeatherBean weatherBean = new Gson().fromJson(weatherStr, WeatherBean.class);
            if (weatherBean != null) {
                Log.d("WeatherActivity", "from sp");
                showWeather(weatherBean);
            }
        }

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(() -> getWeather(mWeatherId));

        btnNavigation.setOnClickListener(v -> showDialog(dialog));
    }

    // 显示地址选择框
    private void showDialog(ChooseAreaDialog dialog) {
        if (dialog.isAdded()) {
            dialog.showDialog();
        } else {
            dialog.show(getSupportFragmentManager(), DIALOG_TAG);
        }
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mObservable = RxBus.getBus().register(ChooseAreaDialog.TAG);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getBus().unregister(ChooseAreaDialog.TAG, mObservable);
        mWeatherModel.clear();
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, WeatherActivity.class));
    }
}
