package com.sleticalboy.doup.baidumap.search;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.sleticalboy.doup.baidumap.BaseMapActivity;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public abstract class BasePoiSearchActivity extends BaseMapActivity implements OnGetPoiSearchResultListener {

    protected PoiSearch mPoiSearch;
    protected PoiOverlay mPoiOverlay;

    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }

    @Override
    protected final void init() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mPoiOverlay = new PoiOverlay(mBaiduMap) {
            @Override
            public boolean onPoiClick(int index) {
                return BasePoiSearchActivity.this.onPoiClick(index);
            }
        };
        mBaiduMap.setOnMarkerClickListener(mPoiOverlay);
        initPoiSearch();
    }

    /**
     * 子类可以覆盖此方法
     *
     * @param index poi 索引
     * @return true 表示消费此点击时间，否则 false
     */
    protected boolean onPoiClick(int index) {
        PoiInfo poiInfo = mPoiOverlay.getPoiResult().getAllPoi().get(index);
        showToast(poiInfo.name + ", " + poiInfo.address);
        return true;
    }

    /**
     * 初始化 poi 搜索
     */
    protected abstract void initPoiSearch();

    /**
     * 获取兴趣点信息
     *
     * @param result 结果
     */
    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("没有搜索结果");
            return;
        }
        mPoiOverlay.setData(result);    // 设置搜索结果
        mPoiOverlay.zoomToSpan();       // 将所有的搜索结果在一个屏幕中显示出来
        mPoiOverlay.addToMap();         // 添加到 map
    }
}
