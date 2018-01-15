package com.sleticalboy.doup.module.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.base.IBaseView;
import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.doup.model.weather.WeatherBean;
import com.sleticalboy.doup.module.amap.LocationManager;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.SPUtils;
import com.sleticalboy.util.StrUtils;
import com.sleticalboy.util.ToastUtils;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public class WeatherActivity extends BaseActivity implements IBaseView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "WeatherActivity";

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

    private String mWeatherId = SPUtils.getString(ConstantValue.KEY_WEATHER_ID, null);

    private WeatherPresenter mPresenter;
    private LocationManager mLocationManager;

    @Override
    protected int attachLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void prepareTask() {
        Observable<AMapLocation> observable = RxBus.getBus().register(LocationManager.TAG);
        observable.subscribe(location -> {
            String s = mLocationManager.defaultHandleLocation(location);
            Log.d(TAG, s);
        });
    }

    @Override
    protected void initView() {

        mPresenter = new WeatherPresenter(this, this);

        mLocationManager = LocationManager.getInstance(this);
        mLocationManager.setNeedAddress(true);
        mLocationManager.setInterval(1000 * 60 * 3);
        mLocationManager.startLocation();

        // 从 sp 中获取地区名，如果不为 null，则说明不是第一次展示天气
        String area = SPUtils.getString(ConstantValue.KEY_AREA, null);
        if (StrUtils.isEmpty(area)) {
//            showDistrictDialog();
            titleCity.setText(area);
        }
        String weatherStr = SPUtils.getString(ConstantValue.KEY_WEATHER, null);
        if (!StrUtils.isEmpty(weatherStr)) {
            WeatherBean weatherBean = new Gson().fromJson(weatherStr, WeatherBean.class);
            if (weatherBean != null) {
                Log.d("WeatherActivity", "from sp");
                showWeather(weatherBean);
            }
        }

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
        btnNavigation.setOnClickListener(v -> showDistrictDialog());

        mPresenter.getBgUrl();
    }

    public void showBg(String bgUrl) {
        runOnUiThread(() -> ImageLoader.load(this, imgBg, bgUrl));
    }

    /**
     * 展示天气信息
     *
     * @param weatherBean 天气对象
     */
    public void showWeather(WeatherBean weatherBean) {
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
    }

    /**
     * 显示地址选择框
     */
    public void showDistrictDialog() {
        ToastUtils.showToast(this, "点我干嘛？");
    }

    @Override
    public void onLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onLoadingOver() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(this, "网络错误");
    }

    @Override
    public void onRefresh() {
        if (swipeRefresh.isRefreshing()) {
            mPresenter.getWeather(mWeatherId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnTokenView();
        mLocationManager.destroy();
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, WeatherActivity.class));
    }
}
