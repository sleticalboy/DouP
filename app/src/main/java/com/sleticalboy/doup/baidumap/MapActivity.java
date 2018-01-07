package com.sleticalboy.doup.baidumap;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.util.LogUtils;
import com.sleticalboy.doup.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android Studio.
 * Date: 1/5/18.
 *
 * @author sleticalboy
 */
public class MapActivity extends BaseMapActivity {

    public static final String TAG = "MapActivity";

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


    @Override
    protected void handleLocation(BDLocation location) {
        defaultHandleLocation(location);
    }

    /**
     * 初始化工作
     */
    @Override
    protected void init() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 显示比例尺
        mapView.showScaleControl(true);
        // 隐藏缩放按钮
        mapView.showZoomControls(false);

//        // 旋转地图, 在原来的基础之上旋转
//        float rotate = mBaiduMap.getMapStatus().rotate + 30;
//        MapStatus rotateStatus = new MapStatus.Builder().rotate(rotate).build();
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(rotateStatus));
//
//        // 俯仰
//        float overlook = mBaiduMap.getMapStatus().overlook - 5;
//        MapStatus overlookStatus = new MapStatus.Builder().overlook(overlook).build();
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overlookStatus));
//
//        // 隐藏指南针
//        mBaiduMap.getUiSettings().setCompassEnabled(false);

        // 获取最高缩放级别 20.0
        Log.d(TAG, "mBaiduMap.getMaxZoomLevel():" + mBaiduMap.getMaxZoomLevel());
        // 获取最小缩放级别 3.0
        Log.d(TAG, "mBaiduMap.getMinZoomLevel():" + mBaiduMap.getMinZoomLevel());

        // 对 marker 覆盖物添加点击事件
        mBaiduMap.setOnMarkerClickListener(marker -> {
            if (marker != null) {
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
