package com.sleticalboy.doup.module.baidumap;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.poi.PoiResult;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.baidumap.dialog.MapSearchDialog;
import com.sleticalboy.util.LogUtils;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/5/18.
 *
 * @author sleticalboy
 */
public class MapActivity extends BaseMapActivity {

    public static final String TAG = "MapActivity";
    public static final String TAG_DIALOG = "tag_show_search_dialog";
    public static final String TAG_POI_RESULT = "poi_result";
    public static final String TAG_POI_DETAIL_RESULT = "poi_detail_result";

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.btn_search)
    ImageView btnSearch;

    @BindView(R.id.btn_common)
    Button btnCommon;
    @BindView(R.id.btn_satellite)
    Button btnSatellite;
    @BindView(R.id.btn_traffic)
    Button btnTraffic;
    @BindView(R.id.btn_heat)
    Button btnHeat;

    @BindView(R.id.btn_zoom_out)
    ImageButton btnZoomOut;
    @BindView(R.id.btn_zoom_in)
    ImageButton btnZoomIn;

    private int mCurLevel = DEFAUlT_LEVEL;
    private MapSearchDialog mSearchDialog;
    private Observable<PoiResult> mResultObservable;


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
        // 隐藏缩放按钮
        mMapView.showZoomControls(false);

        mMapView.removeViewAt(1); // 移除 logo
        mMapView.removeViewAt(2); // 移除比例尺

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

        mResultObservable = RxBus.getBus().register(TAG_POI_RESULT);
        mResultObservable.subscribe(result -> {
            PoiOverlay overlay = new PoiOverlay(mBaiduMap);
            overlay.setData(result);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.addToMap();
            overlay.zoomToSpan();
        });
    }

    @Override
    protected void initView() {
        mSearchDialog = new MapSearchDialog();
    }

    @OnClick({R.id.btn_common, R.id.btn_satellite, R.id.btn_traffic, R.id.btn_heat, R.id.btn_zoom_in,
            R.id.btn_zoom_out, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                showSearchDialog();
                break;
            case R.id.btn_zoom_out:
                mCurLevel++;
                updateZoomLevel(mCurLevel);
                break;
            case R.id.btn_zoom_in:
                mCurLevel--;
                updateZoomLevel(mCurLevel);
                break;
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

    private void showSearchDialog() {
        if (mSearchDialog == null) {
            mSearchDialog = new MapSearchDialog();
        }
        mSearchDialog.show(getSupportFragmentManager(), TAG_DIALOG);
    }

    /**
     * 更新地图缩放级别
     *
     * @param level 缩放级别
     */
    private void updateZoomLevel(int level) {
        if (level <= mBaiduMap.getMinZoomLevel()) {
            btnZoomIn.setEnabled(false);
            btnZoomIn.setAlpha(0.5f);
            showToast("已经缩小至最小级别");
        } else if (level >= mBaiduMap.getMaxZoomLevel()) {
            btnZoomOut.setEnabled(false);
            btnZoomOut.setAlpha(0.5f);
            showToast("已放大至最大级别");
        } else {
            btnZoomOut.setEnabled(true);
            btnZoomOut.setAlpha(1.0f);
            btnZoomIn.setEnabled(true);
            btnZoomIn.setAlpha(1.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(level));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getBus().unregister(TAG_POI_RESULT, mResultObservable);
    }
}
