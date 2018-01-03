package com.sleticalboy.doup.fragment.weather;

import android.content.Intent;
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
import com.sleticalboy.doup.dialog.ChooseAreaDialog;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.mvp.model.WeatherModel;
import com.sleticalboy.doup.mvp.model.bean.weather.WeatherBean;
import com.sleticalboy.doup.util.ImageLoader;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
public class WeatherFragment extends Fragment {

    private static final String TAG = "WeatherFragment";

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    public static final String DIALOG_TAG = "ChooseAreaDialog";

    @BindView(R.id.weather_content)
    TextView weatherContent;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private Unbinder unbind;

    private String mUrl;
    private WeatherBean mData;
    public WeatherModel mWeatherModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_weather, container, false);
        unbind = ButterKnife.bind(this, rootView);

        mWeatherModel = new WeatherModel(getActivity());

        initView();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        initData();
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
                    getActivity().runOnUiThread(() -> ImageLoader.loadHigh(getActivity(), imgBg, mUrl));
                }
            }
        });

        getWeather("CN101050109");
    }

    private void initView() {

        initDialog();

        setupSwipeRefreshLayout();
    }

    private void initDialog() {
        ChooseAreaDialog dialog = new ChooseAreaDialog();
        setTargetFragment(WeatherFragment.this, 300);
        dialog.show(getChildFragmentManager(), DIALOG_TAG);
    }

    private void setupSwipeRefreshLayout() {
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
        }
    }

    private void getWeather(String weatherId) {
        mWeatherModel.getWeather(weatherId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherBean -> {
                    // TODO: 12/30/17 获取并展示天气数据
                    Log.d(TAG, "weather = " + weatherBean.HeWeather.get(0).status);
                    mData = weatherBean;
                    weatherContent.setText(weatherBean.HeWeather.get(0).toString());
                }, Throwable::printStackTrace);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
