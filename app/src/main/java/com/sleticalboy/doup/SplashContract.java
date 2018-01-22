package com.sleticalboy.doup;

import com.sleticalboy.base.IBaseView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/21/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface SplashContract {

    interface View extends IBaseView {

        /**
         * 初始化 presenter
         */
        Presenter createPresenter();

        /**
         * 显示 splash 页面
         */
        void showSplash();

        /**
         * 显示主界面
         */
        void showMain();
    }

    interface Presenter {

        /**
         * 跳转主界面
         */
        void toMain();
    }
}
