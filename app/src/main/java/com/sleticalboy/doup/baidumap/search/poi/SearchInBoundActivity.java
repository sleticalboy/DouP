package com.sleticalboy.doup.baidumap.search.poi;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.sleticalboy.doup.baidumap.search.BasePoiSearchActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class SearchInBoundActivity extends BasePoiSearchActivity {

    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }

    @Override
    protected void initPoiSearch() {
        mPoiSearch.searchInBound(createSearchParams());
    }

    /**
     * 创建搜索参数
     *
     * @return PoiBoundSearchOption 对象
     */
    private PoiBoundSearchOption createSearchParams() {
        PoiBoundSearchOption params = new PoiBoundSearchOption();
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(40.090513, 116.39361))
                .include(new LatLng(40.000513, 116.20361))
                .build();
        params.bound(bounds);
        params.keyword("汉庭");
        return params;
    }

    /**
     * 获取兴趣点详情信息
     *
     * @param detailResult 详细结果
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult detailResult) {

    }
}
