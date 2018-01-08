package com.sleticalboy.doup.baidumap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.util.LogUtils;
import com.sleticalboy.doup.util.RxBus;
import com.sleticalboy.doup.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/6/18.
 *
 * @author sleticalboy
 */
public abstract class BaseMapActivity extends AppCompatActivity {

    public static final String TAG = "BaseMapActivity";

    @BindView(R.id.map_view)
    public MapView mMapView;

    protected BaiduMap mBaiduMap;
    private Observable<BDLocation> mObservable;
    private LocationManager mLocationManager;

    protected static final int DEFAUlT_LEVEL = 15;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        // 注册 RxBus，接收定位返回的 BDLocation
        mObservable = RxBus.getBus().register(LocationManager.TAG);

        mLocationManager = LocationManager.getInstance().init(this);
        mLocationManager.start();

        mObservable.subscribe(this::handleLocation);

        // 获取地图控制器
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(DEFAUlT_LEVEL));

        initView();

        init();
    }

    /**
     * 初始化控件
     */
    protected void initView() {
    }

    /**
     * 解析定位得到的位置
     *
     * @param location 位置对象
     */
    protected abstract void handleLocation(BDLocation location);

    /**
     * 初始化工作
     */
    protected abstract void init();

    /**
     * 移动到天安门
     */
    protected void animate2tam() {
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(LocationManager.TAM);
        mBaiduMap.animateMapStatus(statusUpdate, 500);
    }

    /**
     * 默认处理位置的方法, 定位到当前位置
     *
     * @param location 定位返回的位置
     */
    protected void defaultHandleLocation(BDLocation location) {
        if (mBaiduMap == null) {
            return;
        }

        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        mBaiduMap.setMyLocationData(locationData);

        // 当前位置坐标封装
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        // 创建 status
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(currentLocation);
        // 移动到当前位置
        mBaiduMap.animateMapStatus(mapStatusUpdate, 500);
        showToast(location.getAddrStr());
    }

    /**
     * 逆地理编码：由坐标解析出详细的地址信息
     * 地理编码：由地址解析出坐标
     *
     * @param latLng 经纬度对象
     */
    protected void reverseGeoCode(LatLng latLng) {

        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                // 获取地理编码结果
                String address = geoCodeResult.getAddress();
                LatLng position = geoCodeResult.getLocation();
                LogUtils.d(TAG, "address = " + address);
                LogUtils.d(TAG, "latitude = " + position.latitude);
                LogUtils.d(TAG, "longitude = " + position.longitude);
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                // 获取逆地理编码结果
                String address = reverseGeoCodeResult.getAddress();
                LogUtils.d(TAG, "address = " + address);
                ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                String province = addressDetail.province;
                LogUtils.d(TAG, "province = " + province);
            }
        });
        geoCoder.destroy();
    }

    protected void showToast(CharSequence text) {
        ToastUtils.showToast(this, text);
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        mLocationManager.stop();
        RxBus.getBus().unregister(LocationManager.TAG, mObservable);
    }
}
