package com.sleticalboy.doup.module.amap;

import android.content.Context;

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
public class MainMapPresenter extends BasePresenter implements MainMapContract.IMapMapPresenter {

    public MainMapContract.IMainMapView mMapView;

    public MainMapPresenter(Context context, MainMapContract.IMainMapView mapView) {
        super(context);
        mMapView = mapView;
    }

    @Override
    public void doSearch() {
    }
}
