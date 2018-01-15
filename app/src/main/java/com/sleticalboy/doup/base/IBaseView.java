package com.sleticalboy.doup.base;

/**
 * <pre>
 *   Created by Android Studio.
 *   页面展示基类，包括：正在加载、加载完成、网络异常
 *   Date: 1/3/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface IBaseView {

    /**
     * 加载界面时调用
     */
    void onLoading();

    /**
     * 加载界面完成时调用
     */
    void onLoadingOver();

    /**
     * 网络异常时调用
     */
    void onNetError();
}
