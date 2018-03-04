package com.sleticalboy.doup.module.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.model.weather.WeatherModel;
import com.sleticalboy.doup.model.weather.WeatherBean;
import com.sleticalboy.util.SPUtils;
import com.sleticalboy.util.ToastUtils;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class AutoUpdateService extends Service {

    private WeatherModel mWeatherModel;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWeatherModel = new WeatherModel(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingImg();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long anHour = 1000 * 60 * 60 * 8;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        assert manager != null;
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    // 更新背景图
    private void updateBingImg() {
        HttpUtils.request(ApiConstant.BASE_WEATHER_URL + "bing_pic", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ToastUtils.INSTANCE.showToast(AutoUpdateService.this, "网络错误");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String bingPic = responseBody.string();
                    SPUtils.INSTANCE.putString(ConstantValue.Companion.getKEY_BG(), bingPic);
                }
            }
        });
    }

    // 更新天气
    private void updateWeather() {
        String weatherStr = SPUtils.INSTANCE.getString(ConstantValue.Companion.getKEY_WEATHER(), null);
        if (weatherStr != null) {
            WeatherBean weatherBean = new Gson().fromJson(weatherStr, WeatherBean.class);
            String weatherId = "";
            if (weatherBean != null)
                weatherId = weatherBean.HeWeather.get(0).basic.weatherId;
            if (TextUtils.isEmpty(weatherId))
                weatherId = SPUtils.INSTANCE.getString(ConstantValue.Companion.getKEY_WEATHER_ID(), null);
            if (TextUtils.isEmpty(weatherId))
                return;
            mWeatherModel.getWeather(weatherId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weather -> {
                        Log.d("AutoUpdateService", "from service -->");
                        SPUtils.INSTANCE.putString(ConstantValue.Companion.getKEY_WEATHER(), new Gson().toJson(weather));
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWeatherModel.clear();
    }

    public static void actionStart(Context context) {
        context.startService(new Intent(context, AutoUpdateService.class));
    }
}
