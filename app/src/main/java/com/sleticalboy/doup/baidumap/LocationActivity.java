package com.sleticalboy.doup.baidumap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.util.RxBus;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public class LocationActivity extends BaseActivity {

    public static final String TAG = "LocationActivity";

    private LocationManager mLocationManager;
    private Observable<BDLocation> mObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager.start();

        mObservable = RxBus.getBus().register(LocationManager.TAG);
        mObservable.subscribe(location -> {
            Log.d(TAG, "location --> " + location);
            LocationManager.resolveLocation(location);
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        mLocationManager = LocationManager.getInstance().init(this);
        return R.layout.activity_location;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.stop();

        RxBus.getBus().unregister(LocationManager.TAG, mObservable);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, LocationActivity.class));
    }
}
