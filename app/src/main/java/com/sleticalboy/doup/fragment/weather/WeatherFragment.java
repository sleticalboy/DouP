package com.sleticalboy.doup.fragment.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.MainActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.dialog.ChooseAreaDialog;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.HttpUtils;
import com.sleticalboy.doup.mvp.model.WeatherModel;
import com.sleticalboy.doup.mvp.model.bean.weather.County;
import com.sleticalboy.doup.mvp.model.bean.weather.WeatherBean;
import com.sleticalboy.doup.util.ImageLoader;
import com.sleticalboy.doup.util.RxBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
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
    private ChooseAreaDialog mChooseAreaDialog;
    private Observable<County> mObservable;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mObservable = RxBus.getBus().register(WeatherFragment.TAG);
        Log.d(TAG, "mObservable:" + mObservable);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container
                + "], savedInstanceState = [" + savedInstanceState + "]");

        View rootView = inflater.inflate(R.layout.frag_weather, container, false);
        unbind = ButterKnife.bind(this, rootView);

        mWeatherModel = new WeatherModel(getActivity());

        initView();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = ["
                + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
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

        mObservable.subscribe(county -> {
            MainActivity activity = (MainActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(county.name);
            }
            getWeather(county.weatherId);
        });
    }

    private void initView() {
        initDialog();
        setupSwipeRefreshLayout();
    }

    private void initDialog() {
        mChooseAreaDialog = new ChooseAreaDialog();
        showDialog();
    }

    private void showDialog() {
        mChooseAreaDialog.show(getChildFragmentManager(), DIALOG_TAG);
    }

    private void setupSwipeRefreshLayout() {
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
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        mWeatherModel.clear();
        RxBus.getBus().unregister(WeatherFragment.TAG, mObservable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
        unbind.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }
}
