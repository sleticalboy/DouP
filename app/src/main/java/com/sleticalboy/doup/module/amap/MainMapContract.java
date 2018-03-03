package com.sleticalboy.doup.module.amap;

import com.amap.api.services.poisearch.PoiSearch;
import com.sleticalboy.base.IBaseView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/18/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface MainMapContract {

    interface IMainMapView extends IBaseView {

        /**
         * 显示搜索结果
         */
        void showSearchResult();

        /**
         * 显示指南针
         */
        void showCompass();

        /**
         * 显示缩放按钮
         */
        void showScaleController();
    }

    interface IMapMapPresenter {

        /**
         * 搜索
         */
        void doSearch(PoiSearch.Query query);
    }
}
