package com.sleticalboy.doup.module.amap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.sleticalboy.annotation.BindView;
import com.sleticalboy.doup.R;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MainMapActivity extends CheckPermissionsActivity implements
        MainMapContract.View {

    @BindView(R.id.map_view)
    private MapView mapView;
    //    @BindView(R.id.img_user_icon)
//    CircleImageView imgUserIcon;
    @BindView(R.id.tv_search)
    private TextView tvSearch;
    @BindView(R.id.btn_search)
    private ImageView btnSearch;
    //    @BindView(R.id.ll_header)
//    LinearLayout llHeader;
    @BindView(R.id.btn_common)
    private Button btnCommon;
    @BindView(R.id.btn_satellite)
    private Button btnSatellite;
    @BindView(R.id.btn_traffic)
    private Button btnTraffic;
    @BindView(R.id.btn_heat)
    private Button btnHeat;
    //    @BindView(R.id.ll_map_type)
//    LinearLayout llMapType;
    @BindView(R.id.btn_zoom_out)
    private ImageButton btnZoomOut;
    @BindView(R.id.btn_zoom_in)
    private ImageButton btnZoomIn;

    private AMap mAMap;
    private UiSettings mUiSettings;

    private MainMapPresenter mPresenter;

    @Override
    protected void beforeViews() {
    }

    @Override
    protected void initView(final Bundle savedInstanceState) {
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
