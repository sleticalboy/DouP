package com.sleticalboy.doup.module.baidumap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.sleticalboy.doup.BuildConfig;
import com.sleticalboy.util.RxBus;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 *         <p>
 *         LocationManager
 */
public class LocationManager {

    public static final String TAG = "LocationManager";

    // 天安门经纬度 116.403963,39.915119
    public static final double TAM_LATITUDE = 39.915119;
    public static final double TAM_LONGITUDE = 116.403963;
    public static final LatLng TAM = new LatLng(TAM_LATITUDE, TAM_LONGITUDE);

    // latitude = 4.9E-324, longitude = 4.9E-324
    public static final double ERROR_LATITUDE = 4.9E-324;
    public static final double ERROR_LONGITUDE = ERROR_LATITUDE;

    private static LocationManager sInstance;

    private LocationClient mLocationClient;

    private LocationManager() {
    }

    public synchronized static LocationManager getInstance() {
        if (sInstance == null)
            sInstance = new LocationManager();
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param context Context 对象
     * @return LocationManager 对象
     */
    @NonNull
    public LocationManager init(@NonNull Context context) {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context.getApplicationContext());
        }

        mLocationClient.registerLocationListener(new MyLocationListener());

        LocationClientOption option = new LocationClientOption();

        // 可选，设置定位模式，默认高精度
        // Hight_Accuracy：高精度；
        // Battery_Saving：低功耗；
        // Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 可选，设置返回经纬度坐标类型，默认gcj02
        // gcj02：国测局坐标；
        // bd09ll：百度经纬度坐标；
        // bd09：百度墨卡托坐标；
        // 海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        // 可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000 * 60 * 3);

        // 可选，设置是否使用gps，默认false
        // 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        // 可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        // 可选，定位SDK内部是一个service，并放到了独立进程。
        // 设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(true);

        // 可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        // 可选，7.2版本新增能力
        // 如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
//        option.setWifiCacheTimeOut(60 * 1000 * 5);

        // 可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        // 可选，是否需要地址信息，默认为不需要，即参数为false
        // 如果开发者需要获得当前点的地址信息，此处必须为true
        //  option.setAddrType("all"); // 已过时的方法，用下面的方法替代, true 表示 all， false 表示 noaddr
        option.setIsNeedAddress(true);

        // 可选，是否需要位置描述信息，默认为不需要，即参数为false
        // 如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);

        // 可选，是否需要周边POI信息，默认为不需要，即参数为false
        // 如果开发者需要获得周边POI信息，此处必须为true
        option.setIsNeedLocationPoiList(true);

        // mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        // 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        // 新方法
        //调用LocationClient的requestHotSpotState()方法，便可发起请求，获取设备所链接网络信息
//        mLocationClient.requestHotSpotState();

        return this;
    }

    /**
     * 解析位置信息
     *
     * @param location 位置
     */
    public static void resolveLocation(BDLocation location) {

        // 获取经纬度
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d(TAG, "original data --> latitude = " + latitude + ", longitude = " + longitude);

        if (latitude == ERROR_LATITUDE && longitude == ERROR_LONGITUDE) {
            location.setLatitude(TAM_LATITUDE);
            location.setLongitude(TAM_LONGITUDE);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d(TAG, "after redirect --> latitude = " + latitude + ", longitude = " + longitude);
        }


        // 获取地址信息
        String address = location.getAddrStr();
        String province = location.getProvince();
        String city = location.getCity();
        location.getCityCode();
        String country = location.getCountry();
        location.getCountryCode();
        String district = location.getDistrict();
        String street = location.getStreet();

        Log.d(TAG, "address = " + address);
        Log.d(TAG, "country = " + country);
        Log.d(TAG, "city = " + city);
        Log.d(TAG, "province = " + province);
        Log.d(TAG, "district = " + district);
        Log.d(TAG, "street = " + street);

        // 获取位置描述
        String describe = location.getLocationDescribe();
        Log.d(TAG, "describe = " + describe);

        // 获取周边 poi
        List<Poi> poiList = location.getPoiList();
        if (poiList != null && poiList.size() > 0) {
            for (Poi poi : poiList) {
                Log.d(TAG, "poi.getId(): " + poi.getId());
                Log.d(TAG, "poi.getRank(): " + poi.getRank());
                Log.d(TAG, "poi.getName(): " + poi.getName());
            }
        }

        // 国内外信息
        // BDLocation.LOCATION_WHERE_IN_CN：当前定位点在国内；
        // BDLocation.LOCATION_WHERE_OUT_CN：当前定位点在海外；
        // 其他：无法判定。
        int where = location.getLocationWhere();
        switch (where) {
            case BDLocation.LOCATION_WHERE_IN_CN:
                Log.d(TAG, "国内");
                break;
            case BDLocation.LOCATION_WHERE_OUT_CN:
                Log.d(TAG, "国外");
                break;
            case BDLocation.LOCATION_WHERE_UNKNOW:
            default:
                Log.d(TAG, "未知位置");
                break;
        }
    }

    /**
     * 开始定位
     */
    public void start() {
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    /**
     * 结束定位
     */
    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    static class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 获取经纬度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.d(TAG, "original data --> latitude = " + latitude + ", longitude = " + longitude);

            if (latitude == ERROR_LATITUDE && longitude == ERROR_LONGITUDE) {
                location.setLatitude(TAM_LATITUDE);
                location.setLongitude(TAM_LONGITUDE);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d(TAG, "after redirect --> latitude = " + latitude + ", longitude = " + longitude);
            }

            // 把定位得到的 Location 结果 post 出去
            RxBus.getBus().post(TAG, location);

            if (BuildConfig.DEBUG) {
                resolveLocation(location);
            }
        }
    }
}
