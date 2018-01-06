package com.sleticalboy.doup.baidumap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/5/18.
 *
 * @author sleticalboy
 */
public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActivity";

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.btn_common)
    Button btnCommon;
    @BindView(R.id.btn_satellite)
    Button btnSatellite;
    @BindView(R.id.btn_traffic)
    Button btnTraffic;
    @BindView(R.id.btn_heat)
    Button btnHeat;

    @BindView(R.id.btn_location)
    ImageButton btnLocation;

    @BindView(R.id.btn_navigation)
    FloatingActionButton btnNavigation;

    private BaiduMap mBaiduMap;
    private LocationManager mLocationManager;
    private Observable<BDLocation> mObservable;

    // 构建 marker
    private BitmapDescriptor mBitmap1 = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
    private BitmapDescriptor mBitmap2 = BitmapDescriptorFactory.fromResource(R.mipmap.icon_markb);

    private Marker mMarker1;
    private double mLatitude;
    private double mLongitude;
    private boolean mIsFirstLoc = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        // 初始化定位
        mLocationManager = LocationManager.getInstance().init(this);

        // MapView 一共有四个子 View
        // mapView.removeViewAt(1); // 移除 logo ImageView
        // mapView.removeViewAt(2); // 移除比例尺

        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapStatus(statusUpdate);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 开启定位
        mLocationManager.start();
        // 注册 RxBus，获取定位返回的 BDLocation
        mObservable = RxBus.getBus().register(LocationManager.TAG);

        mObservable.subscribe(location -> {
            Log.d(TAG, "location.getLocType():" + location.getLocType());
            Log.d(TAG, "location.getLatitude():" + location.getLatitude());
            Log.d(TAG, "location.getLongitude():" + location.getLongitude());
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

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            if (mIsFirstLoc) {
                mIsFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                ToastUtils.showToast(this, location.getAddrStr());
            }
        });

        initBaiduMap();
    }

    /**
     * 初始化地图
     */
    private void initBaiduMap() {

        // 对 marker 覆盖物添加点击事件
        mBaiduMap.setOnMarkerClickListener(marker -> {
            if (mMarker1 == marker) {
                final LatLng latLng = marker.getPosition();
                // 将经纬度转换成屏幕上的点
                mBaiduMap.getProjection().toScreenLocation(latLng);
                ToastUtils.showToast(MapActivity.this, latLng.toString());
            }
            return false;
        });

        // 对 Marker 覆盖物添加拖拽事件
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng position = marker.getPosition();
                if (position != null) {
                    String msg = "拖拽结束，新位置: latitude --> " + position.latitude
                            + ", longitude --> " + position.longitude;
                    LogUtils.d(TAG, "onMarkerDrag = " + msg);
                    // 获取经纬度之后进行反向地理编码，获取具体的位置信息
                    reverseGeoCode(position);
                }
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // 拖拽结束，确定了最终的位置，可以最一些后续的工作
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                // 开始拖拽
            }
        });

        // 点击地图时触发的事件监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LogUtils.d(TAG, "latLng:" + latLng);
                displayInfoWindow(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    /**
     * 逆地理编码：由坐标解析出详细的地址信息
     * 地理编码：由地址解析出坐标
     *
     * @param latLng 经纬度对象
     */
    private void reverseGeoCode(LatLng latLng) {

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

    /**
     * 显示弹出窗口覆盖物
     *
     * @param latLng 经纬度对象
     */
    private void displayInfoWindow(LatLng latLng) {
        Button button = new Button(this);
        button.setBackgroundResource(R.mipmap.popup);
        button.setText("点我～");
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(button);

        mBaiduMap.showInfoWindow(new InfoWindow(bitmapDescriptor, latLng, -47,
                () -> {
                    reverseGeoCode(latLng);
                    mBaiduMap.hideInfoWindow();
                })
        );
    }

    /**
     * 设置多边形覆盖物
     *
     * @param latLng 经纬度对象
     */
    private void setOverlay(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        LatLng p1 = new LatLng(latitude + 0.02, longitude);
        LatLng p2 = new LatLng(latitude, longitude - 0.03);
        LatLng p3 = new LatLng(latitude - 0.02, longitude - 0.01);
        LatLng p4 = new LatLng(latitude - 0.02, longitude + 0.01);
        LatLng p5 = new LatLng(latitude - 0.02, longitude + 0.03);

        List<LatLng> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);

        PolygonOptions options = new PolygonOptions();
        options.points(points);
        options.fillColor(0xaaffff00);
        options.stroke(new Stroke(2, 0xaa00ff00));
        // 设置多边形覆盖物
        mBaiduMap.addOverlay(options);
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();

        mLocationManager.stop();

        RxBus.getBus().unregister(LocationManager.TAG, mObservable);
    }

    @OnClick({R.id.btn_common, R.id.btn_satellite, R.id.btn_traffic, R.id.btn_heat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_none:
//                // 空白图层 节省流量，加载速度快
//                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
//                break;
            case R.id.btn_common:
                // 普通地图 显示基础的道路地图，有道路，建筑物，绿地，河流等
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                btnCommon.setEnabled(false);
                btnSatellite.setEnabled(true);
                break;
            case R.id.btn_satellite:
                // 卫星地图 显示卫星照片数据
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                btnSatellite.setEnabled(false);
                btnCommon.setEnabled(true);
                break;
            case R.id.btn_traffic:
                // 实时路况
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    ToastUtils.showToast(this, "关闭实时路况");
                } else {
                    mBaiduMap.setTrafficEnabled(true);
                    ToastUtils.showToast(this, "打开实时路况");
                }
                break;
            case R.id.btn_heat:
                // 热力地图
                if (mBaiduMap.isBaiduHeatMapEnabled()) {
                    mBaiduMap.setBaiduHeatMapEnabled(false);
                    ToastUtils.showToast(this, "关闭热力图");
                } else {
                    mBaiduMap.setBaiduHeatMapEnabled(true);
                    ToastUtils.showToast(this, "打开热力图");
                }
                break;
        }
    }
}
