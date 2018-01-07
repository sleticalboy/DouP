package com.sleticalboy.doup.mvp.base;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public interface IBaseView<T> {

    /**
     * 显示加载动画
     */
    void onShowLoading();

    /**
     * 隐藏加载动画
     */
    void onHiddenLoading();

    /**
     * 网络错误
     */
    void onNetError();

    /**
     * 设置 presenter
     */
    void setPresenter(T presenter);
}
