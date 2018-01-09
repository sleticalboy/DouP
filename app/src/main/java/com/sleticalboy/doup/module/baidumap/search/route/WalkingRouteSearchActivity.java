package com.sleticalboy.doup.module.baidumap.search.route;

import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.sleticalboy.doup.module.baidumap.search.BaseRouteSearchActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class WalkingRouteSearchActivity extends BaseRouteSearchActivity {

    @Override
    protected void initRouteSearch() {
        mRouteSearch.walkingSearch(createWalkingSearchParams());
    }

    private WalkingRoutePlanOption createWalkingSearchParams() {
        WalkingRoutePlanOption params = new WalkingRoutePlanOption();
        params.from(PlanNode.withCityNameAndPlaceName("北京", "北京大学"));
        params.to(PlanNode.withLocation(mEnd)); // 天安门
        return params;
    }

    /**
     * 步行
     *
     * @param walkingRouteResult 步行路线搜索结果
     */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("没有搜索到结果");
            return;
        }

        WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
        overlay.setData(walkingRouteResult.getRouteLines().get(0));
        mBaiduMap.setOnMarkerClickListener(overlay);
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    /**
     * 换乘（公交、地铁）
     *
     * @param transitRouteResult 换乘路线搜索结果
     */
    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
    }

    /**
     * 驾车
     *
     * @param drivingRouteResult 驾车路线搜索结果
     */
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
    }
}
