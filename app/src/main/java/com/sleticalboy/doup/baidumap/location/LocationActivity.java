package com.sleticalboy.doup.baidumap.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.sleticalboy.doup.BuildConfig;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.baidumap.LocationManager;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.util.LogUtils;
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

            ////////////////////////////////////////////

            //Receive Location
            StringBuilder builder = new StringBuilder(256);
            builder.append("time : ");
            builder.append(location.getTime());
            builder.append("\nerror code : ");
            builder.append(location.getLocType());
            builder.append("\nlatitude : ");
            builder.append(location.getLatitude());
            builder.append("\nlongitude : ");
            builder.append(location.getLongitude());
            builder.append("\nradius : ");
            builder.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                builder.append("\nspeed : ");
                builder.append(location.getSpeed());// 单位：公里每小时
                builder.append("\nsatellite : ");
                builder.append(location.getSatelliteNumber());
                builder.append("\nheight : ");
                builder.append(location.getAltitude());// 单位：米
                builder.append("\ndirection : ");
                builder.append(location.getDirection());// 单位度
                builder.append("\naddr : ");
                builder.append(location.getAddrStr());
                builder.append("\ndescribe : ");
                builder.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                builder.append("\naddr : ");
                builder.append(location.getAddrStr());
                //运营商信息
                builder.append("\noperationers : ");
                builder.append(location.getOperators());
                builder.append("\ndescribe : ");
                builder.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                builder.append("\ndescribe : ");
                builder.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                builder.append("\ndescribe : ");
                builder.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                builder.append("\ndescribe : ");
                builder.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                builder.append("\ndescribe : ");
                builder.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            builder.append("\nlocationdescribe : ");
            builder.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                builder.append("\npoilist size = : ");
                builder.append(list.size());
                for (Poi p : list) {
                    builder.append("\npoi= : ");
                    builder.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            /////////////////////////////////////////////

            // 国内外信息
            // BDLocation.LOCATION_WHERE_IN_CN：当前定位点在国内；
            // BDLocation.LOCATION_WHERE_OUT_CN：当前定位点在海外；
            // 其他：无法判定。
            int where = location.getLocationWhere();
            builder.append("\n位置：");
            switch (where) {
                case BDLocation.LOCATION_WHERE_IN_CN:
                    builder.append("国内");
                    break;
                case BDLocation.LOCATION_WHERE_OUT_CN:
                    builder.append("国外");
                    break;
                case BDLocation.LOCATION_WHERE_UNKNOW:
                default:
                    builder.append("未知位置");
                    break;
            }
            LogUtils.d(TAG, "location info --> " + builder.toString());
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
