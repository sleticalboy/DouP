package com.sleticalboy.base;

import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *     列表页面基类，包括：获取 RecyclerView、加载更多、没有更多
 *   Date: 1/3/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface IBaseListView extends IBaseView {

    /**
     * 设置适配器
     */
    void setAdapter(RecyclerArrayAdapter adapter);

    /**
     * 设置 LayoutManager
     */
    void setLayoutManager();
}
