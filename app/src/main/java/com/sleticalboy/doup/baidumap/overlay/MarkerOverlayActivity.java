package com.sleticalboy.doup.baidumap.overlay;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.baidumap.BaseMapActivity;
import com.sleticalboy.doup.baidumap.location.LocationManager;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class MarkerOverlayActivity extends BaseMapActivity {

    private TextView tvTitle;

    private View mPopView;

    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }

    @Override
    protected void init() {

        initMarker();

        mBaiduMap.setOnMarkerClickListener(marker -> {
            if (mPopView == null) {
                mPopView = View.inflate(MarkerOverlayActivity.this, R.layout.item_marker_pop, null);
                tvTitle = mPopView.findViewById(R.id.tv_title);
                mMapView.addView(mPopView, createLayoutParams(marker.getPosition()));
            } else {
                mMapView.updateViewLayout(mPopView, createLayoutParams(marker.getPosition()));
            }
            tvTitle.setText(marker.getTitle());
            return true;
        });

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                mMapView.updateViewLayout(mPopView, createLayoutParams(marker.getPosition()));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMapView.updateViewLayout(mPopView, createLayoutParams(marker.getPosition()));
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                mMapView.updateViewLayout(mPopView, createLayoutParams(marker.getPosition()));
            }
        });
    }

    /**
     * 创建 MapView 的布局参数
     *
     * @param latLng 位置坐标
     * @return MapViewLayoutParams
     */
    @NonNull
    private MapViewLayoutParams createLayoutParams(LatLng latLng) {
        return new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode) // 地图坐标模式
                .position(latLng)   // 位置
                .yOffset(-25)       // 向上偏移
                .build();
    }

    private void initMarker() {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_eat);

        MarkerOptions options = new MarkerOptions()
                .position(LocationManager.TAM)
                .title("A Marker")
                .icon(icon)
                .draggable(true);
        mBaiduMap.addOverlay(options);

        // 添加一个向北的标志
        options = new MarkerOptions()
                .icon(icon)
                .title("向北")
                .position(new LatLng(LocationManager.TAM.latitude + 0.001, LocationManager.TAM.longitude))
                .draggable(true);
        mBaiduMap.addOverlay(options);

        // 添加一个向东的标志
        options = new MarkerOptions()
                .icon(icon)
                .title("向东")
                .position(new LatLng(LocationManager.TAM.latitude, LocationManager.TAM.longitude + 0.001))
                .draggable(true);
        mBaiduMap.addOverlay(options);

        // 添加一个向西南的标志
        options = new MarkerOptions()
                .icon(icon)
                .title("向西南")
                .position(new LatLng(LocationManager.TAM.latitude - 0.001, LocationManager.TAM.longitude - 0.001))
                .draggable(true);
        mBaiduMap.addOverlay(options);
    }
}
