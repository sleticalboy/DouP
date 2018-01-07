package com.sleticalboy.doup.baidumap.search.poi;

import android.support.annotation.NonNull;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.sleticalboy.doup.baidumap.search.BasePoiSearchActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class SearchInCityActivity extends BasePoiSearchActivity {

    private int mPageNum;

    @Override
    protected void initPoiSearch() {
        mPoiSearch.searchInCity(createSearchParams());
    }

    @Override
    protected boolean onPoiClick(int index) {
        PoiInfo poiInfo = mPoiOverlay.getPoiResult().getAllPoi().get(index);
        mPoiSearch.searchPoiDetail(createSearchDetailParams(poiInfo.uid));
        return true;
    }

    /**
     * 创建详细搜索参数
     *
     * @param poiUid poiUid
     * @return PoiDetailSearchOption 对象
     */
    private PoiDetailSearchOption createSearchDetailParams(@NonNull String poiUid) {
        PoiDetailSearchOption params = new PoiDetailSearchOption();
        params.poiUid(poiUid);
        return params;
    }

    /**
     * 创建搜索参数
     *
     * @return PoiCitySearchOption 对象
     */
    private PoiCitySearchOption createSearchParams() {
        PoiCitySearchOption params = new PoiCitySearchOption();
        params.city("北京");
        params.keyword("汉庭");
        params.pageCapacity(10);
        params.pageNum(mPageNum);
        return params;
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult detailResult) {
        if (detailResult == null || detailResult.error != PoiResult.ERRORNO.NO_ERROR) {
            showToast("没有搜索结果");
            return;
        }
        String address = detailResult.getAddress();
        String shopHours = detailResult.getShopHours();
        showToast("address = " + address + ", " + shopHours);
    }
}
