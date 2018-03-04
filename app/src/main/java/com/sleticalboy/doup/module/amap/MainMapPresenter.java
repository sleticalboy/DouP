package com.sleticalboy.doup.module.amap;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sleticalboy.base.BasePresenter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/18/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MainMapPresenter extends BasePresenter implements
        MainMapContract.Presenter, PoiSearch.OnPoiSearchListener {

    public MainMapContract.View mMapView;
    private final PoiSearch mPoiSearch;

    public MainMapPresenter(Context context, MainMapContract.View mapView) {
        super(context);
        mMapView = mapView;
        mPoiSearch = new PoiSearch(context, null);
    }

    @Override
    public void doSearch(PoiSearch.Query query) {
        mPoiSearch.setQuery(query);
        mPoiSearch.searchPOIAsyn();
        mPoiSearch.setOnPoiSearchListener(this);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        //
    }
}
