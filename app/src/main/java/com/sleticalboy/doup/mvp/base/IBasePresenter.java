package com.sleticalboy.doup.mvp.base;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public interface IBasePresenter {

    /**
     * 刷新数据
     */
    void doRefreshData();

    /**
     * 显示网络错误
     */
    void doShowNetError();
}
