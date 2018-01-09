package com.sleticalboy.doup.module.baidumap.search.route;

import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.sleticalboy.doup.module.baidumap.search.BaseRouteSearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class DrivingRouteSearchActivity extends BaseRouteSearchActivity {

    @Override
    protected void initRouteSearch() {
        mRouteSearch.drivingSearch(createDrivingSearchParams());
    }

    private DrivingRoutePlanOption createDrivingSearchParams() {
        DrivingRoutePlanOption params = new DrivingRoutePlanOption();
        params.from(PlanNode.withCityNameAndPlaceName("北京", "清华大学")); // 设置起点

        List<PlanNode> nodes = new ArrayList<>();
        nodes.add(PlanNode.withCityNameAndPlaceName("北京", "北京大学")); // 设置途径点
        params.passBy(nodes);

        params.to(PlanNode.withLocation(mEnd)); // 设置终点
        return params;
    }

    /**
     * 步行
     *
     * @param walkingRouteResult 步行路线搜索结果
     */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

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
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("没有搜索到任何结果");
            return;
        }
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(overlay);

        // 最优路线
        overlay.setData(drivingRouteResult.getRouteLines().get(0));

        overlay.addToMap();
        overlay.zoomToSpan();
    }
}
