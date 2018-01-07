package com.sleticalboy.doup.mvp.base;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public interface IBaseListView<T> extends IBaseView<T> {

    /**
     * 设置适配器
     *
     * @param dataList
     */
    void onSetAdapter(List<?> dataList);

    /**
     * 加载完毕
     */
    void onShowNoMore();
}
