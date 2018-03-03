package com.sleticalboy.doup.module.amap;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.sleticalboy.util.LogUtils;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.TimeUtils;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public final class LocationManager implements AMapLocationListener {

    public static final String TAG = "LocationManager";

    private static LocationManager sInstance;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private LocationManager(Context context) {
        init(context);
    }

    public static synchronized LocationManager getInstance(@NonNull Context context) {
        if (sInstance == null)
            sInstance = new LocationManager(context.getApplicationContext());
        return sInstance;
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    /**
     * 销毁定位，释放资源
     */
    public void destroy() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    /**
     * 设置是否需要显示地址信息
     */
    public void setNeedAddress(boolean needAddress) {
        getDefaultOption().setNeedAddress(needAddress);
    }

    /**
     * 设置是否优先返回GPS定位结果
     */
    public void setGpsFirst(boolean first) {
        getDefaultOption().setGpsFirst(first);
    }

    /**
     * 设置是否单次定位
     */
    public void setSingleLocate(boolean single) {
        getDefaultOption().setOnceLocation(single);
    }

    /**
     * 设置是否使用传感器
     */
    public void setEnableSensor(boolean enable) {
        getDefaultOption().setSensorEnable(enable);
    }

    /**
     * 定位请求的时间间隔
     *
     * @param interval 时间间隔
     */
    public void setInterval(long interval) {
        getDefaultOption().setInterval(interval);
    }

    /**
     * 可选， 设置网络请求的协议。默认为 http
     *
     * @param protocol 枚举类型，http 和 https可选
     */
    public void setProtocol(AMapLocationClientOption.AMapLocationProtocol protocol) {
        AMapLocationClientOption.setLocationProtocol(protocol);
    }

    /**
     * 设置网络请求超时时间
     *
     * @param timeOut 超时时长
     */
    public void setTimeOut(long timeOut) {
        getDefaultOption().setHttpTimeOut(timeOut);
    }

    private void init(Context context) {
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationOption = getDefaultOption();
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
    }

    public AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        // 可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        // 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30 * 1000);
        // 可选，设置定位间隔。默认为2秒
        mOption.setInterval(30 * 1000);
        // 可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        // 可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        // 可选，设置是否等待wifi刷新
        // 默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        // 可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        // 可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        // 可选，设置是否开启wifi扫描。
        // 默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        // 可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        return mOption;
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {
            LogUtils.INSTANCE.d(TAG, defaultHandleLocation(location));
            // 将定位结果传递出去，在需要的地方接收
            RxBus.getBus().post(LocationManager.TAG, location);
        }
    }

    /**
     * 定位结果默认处理
     *
     * @param location 定位结果
     */
    public String defaultHandleLocation(AMapLocation location) {
        StringBuilder sb = new StringBuilder();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型\t: " + location.getLocationType() + "\n");
            sb.append("经\t度\t: " + location.getLongitude() + "\n");
            sb.append("纬\t度\t: " + location.getLatitude() + "\n");
            sb.append("精\t度\t: " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者\t\t: " + location.getProvider() + "\n");

            sb.append("速\t度\t: " + location.getSpeed() + "米/秒" + "\n");
            sb.append("角\t度\t: " + location.getBearing() + "\n");
            // 获取当前提供定位服务的卫星个数
            sb.append("星\t数\t: " + location.getSatellites() + "\n");
            sb.append("国\t家\t: " + location.getCountry() + "\n");
            sb.append("省\t\t: " + location.getProvince() + "\n");
            sb.append("市\t\t: " + location.getCity() + "\n");
            sb.append("城市编码\t: " + location.getCityCode() + "\n");
            sb.append("区\t\t: " + location.getDistrict() + "\n");
            sb.append("区域码\t\t: " + location.getAdCode() + "\n");
            sb.append("地\t址\t: " + location.getAddress() + "\n");
            sb.append("兴趣点\t\t: " + location.getPoiName() + "\n");
            //定位完成的时间
            sb.append("定位时间\t: " + TimeUtils.INSTANCE.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        sb.append("***定位质量报告***").append("\n");
        sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
        sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
        sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
        sb.append("****************").append("\n");
        //定位之后的回调时间
        sb.append("回调时间: " + TimeUtils.INSTANCE.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

        //解析定位结果，
        String result = sb.toString();

        return result;
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }
}
