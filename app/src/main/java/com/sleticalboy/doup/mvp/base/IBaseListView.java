package com.sleticalboy.doup.mvp.base;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public interface IBaseListView<P> extends IBaseView<P> {

    /**
     * 设置适配器
     *
     * @param dataList 列表数据
     */
    void onSetAdapter(List<?> dataList);

    /**
     * 加载完毕
     */
    void onShowNoMore();
}
