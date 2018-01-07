package com.sleticalboy.doup.baidumap.search.route;

import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.sleticalboy.doup.baidumap.search.BaseRouteSearchActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class TransitRouteSearchActivity extends BaseRouteSearchActivity {

    @Override
    protected void initRouteSearch() {
        mRouteSearch.transitSearch(createTransitSearchParams());
    }

    private TransitRoutePlanOption createTransitSearchParams() {
        TransitRoutePlanOption params = new TransitRoutePlanOption();
        params.city("北京");
        params.from(PlanNode.withCityNameAndPlaceName("北京", "北京大学"));
        params.to(PlanNode.withLocation(mEnd));
        params.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST);
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
        if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("没有搜索到结果");
            return;
        }
        TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
        overlay.setData(transitRouteResult.getRouteLines().get(0)); // 设置数据
        mBaiduMap.setOnMarkerClickListener(overlay);
        overlay.addToMap();
        overlay.zoomToSpan();
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
