package com.sleticalboy.doup.module.amap;

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
public final class MainMapContract {

    public interface IMainMapView extends IBaseView {

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

    public interface IMapMapPresenter {

        /**
         * 搜索
         */
        void doSearch();
    }
}
