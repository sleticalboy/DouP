package com.sleticalboy.doup.baidumap.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.sleticalboy.doup.BuildConfig;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.util.RxBus;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public class LocationActivity extends BaseActivity {

    public static final String TAG = "LocationActivity";
    @BindView(R.id.tv_show_info)
    TextView tvShowInfo;

    private LocationManager mLocationManager;
    private Observable<BDLocation> mObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager.start();
    }

    @Override
    protected void initData() {
        mObservable = RxBus.getBus().register(LocationManager.TAG);
        mObservable.subscribe(location -> {

            if (BuildConfig.DEBUG) {
                LocationManager.resolveLocation(location);
            }

            StringBuilder builder = new StringBuilder();

            // 获取经纬度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            builder.append("original data --> \n");
            builder.append("latitude = ").append(latitude).append("\n");
            builder.append("longitude = ").append(longitude).append("\n");

            if (latitude == LocationManager.ERROR_LATITUDE && longitude == LocationManager.ERROR_LONGITUDE) {
                builder.append("after wrap --> \n");
                location.setLatitude(LocationManager.TAM_LATITUDE);
                location.setLongitude(LocationManager.TAM_LONGITUDE);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                builder.append("latitude = ").append(latitude).append("\n");
                builder.append("longitude = ").append(longitude).append("\n");
            }


            // 获取地址信息
            builder.append("address = ").append(location.getAddrStr()).append("\n");
            builder.append("country = ").append(location.getCountry()).append("\n");
            builder.append("province = ").append(location.getProvince()).append("\n");
            builder.append("city = ").append(location.getCity()).append("\n");
            builder.append("district = ").append(location.getDistrict()).append("\n");
            builder.append("street = ").append(location.getStreet()).append("\n");

            // 获取位置描述
            builder.append("describe = ").append(location.getLocationDescribe()).append("\n");

            // 获取周边 poi
            List<Poi> poiList = location.getPoiList();
            if (poiList != null && poiList.size() > 0) {
                for (Poi poi : poiList) {
                    builder.append("poi.id = ").append(poi.getId()).append("\n");
                    builder.append("poi.rank = ").append(poi.getRank()).append("\n");
                    builder.append("poi.name = ").append(poi.getName()).append("\n");
                }
            }

            // 国内外信息
            // BDLocation.LOCATION_WHERE_IN_CN：当前定位点在国内；
            // BDLocation.LOCATION_WHERE_OUT_CN：当前定位点在海外；
            // 其他：无法判定。
            int where = location.getLocationWhere();
            builder.append("位置：");
            switch (where) {
                case BDLocation.LOCATION_WHERE_IN_CN:
                    Log.d(TAG, "国内");
                    builder.append("国内");
                    break;
                case BDLocation.LOCATION_WHERE_OUT_CN:
                    Log.d(TAG, "国外");
                    builder.append("国外");
                    break;
                case BDLocation.LOCATION_WHERE_UNKNOW:
                default:
                    Log.d(TAG, "未知位置");
                    builder.append("未知位置");
                    break;
            }
            tvShowInfo.setText(builder.toString());
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        mLocationManager = LocationManager.getInstance().init(this);
        mLocationManager.start();
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
