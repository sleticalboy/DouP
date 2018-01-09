package com.sleticalboy.doup.module.baidumap.search.poi;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.sleticalboy.doup.module.baidumap.LocationManager;
import com.sleticalboy.doup.module.baidumap.search.BasePoiSearchActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class SearchNearbyActivity extends BasePoiSearchActivity {
    @Override
    protected void initPoiSearch() {
        mPoiSearch.searchNearby(createSearchParams());
    }

    /**
     * 创建附近搜索参数
     *
     * @return PoiNearbySearchOption 对象
     */
    private PoiNearbySearchOption createSearchParams() {
        PoiNearbySearchOption params = new PoiNearbySearchOption();
        params.location(LocationManager.TAM);
        params.radius(2000);
        params.keyword("银行");
        return params;
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }
}
