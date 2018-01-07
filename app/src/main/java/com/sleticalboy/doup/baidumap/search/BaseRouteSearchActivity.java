package com.sleticalboy.doup.baidumap.search;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.sleticalboy.doup.baidumap.BaseMapActivity;
import com.sleticalboy.doup.baidumap.LocationManager;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public abstract class BaseRouteSearchActivity extends BaseMapActivity implements OnGetRoutePlanResultListener {

    protected RoutePlanSearch mRouteSearch;
    protected LatLng mEnd = LocationManager.TAM;

    @Override
    protected void init() {
        mRouteSearch = RoutePlanSearch.newInstance();
        mRouteSearch.setOnGetRoutePlanResultListener(this);
        initRouteSearch();
    }

    /**
     * 初始化路线搜索
     */
    protected abstract void initRouteSearch();

    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }
}
