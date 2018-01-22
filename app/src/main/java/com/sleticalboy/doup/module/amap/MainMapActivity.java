package com.sleticalboy.doup.module.amap;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.sleticalboy.doup.R;

import butterknife.BindView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MainMapActivity extends CheckPermissionsActivity implements MainMapContract.IMainMapView {

    @BindView(R.id.map_view)
    MapView mapView;
    //    @BindView(R.id.img_user_icon)
//    CircleImageView imgUserIcon;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    //    @BindView(R.id.ll_header)
//    LinearLayout llHeader;
    @BindView(R.id.btn_common)
    Button btnCommon;
    @BindView(R.id.btn_satellite)
    Button btnSatellite;
    @BindView(R.id.btn_traffic)
    Button btnTraffic;
    @BindView(R.id.btn_heat)
    Button btnHeat;
    //    @BindView(R.id.ll_map_type)
//    LinearLayout llMapType;
    @BindView(R.id.btn_zoom_out)
    ImageButton btnZoomOut;
    @BindView(R.id.btn_zoom_in)
    ImageButton btnZoomIn;

    private AMap mAMap;
    private UiSettings mUiSettings;

    private MainMapPresenter mPresenter;

    @Override
    protected void prepareTask() {
    }

    @Override
    protected void initView() {
        mPresenter = new MainMapPresenter(this, this);
        mAMap = mapView.getMap();
        mUiSettings = mAMap.getUiSettings();
        initMap();
    }

    private void initMap() {
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(null));
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_map;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainMapActivity.class));
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
    }

    @Override
    public void onNetError() {
    }

    @Override
    public void showSearchResult() {
    }

    @Override
    public void showCompass() {
        mUiSettings.setCompassEnabled(!mUiSettings.isCompassEnabled());
    }

    @Override
    public void showScaleController() {
        mUiSettings.setScaleControlsEnabled(!mUiSettings.isScaleControlsEnabled());
    }
}
