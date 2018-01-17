package com.sleticalboy.base;

import com.sleticalboy.widget.recyclerview.EasyRecyclerView;

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
     *
     * @param adapter
     * @param <A>
     */
//    <A extends RecyclerArrayAdapter> void setAdapter(A adapter);

    /**
     * 设置 LayoutManager
     *
     * @param manager
     * @param <M>
     */
//    <M extends RecyclerView.LayoutManager> void setLayoutManager(M manager);

    /**
     * 获取当前页面的 RecyclerView
     *
     * @return EasyRecyclerView 对象
     */
    EasyRecyclerView getRecyclerView();

    /**
     * 没有更多数据时调用
     */
    void onNoMore(); // 对 footer 操作

    /**
     * 加载更多时调用
     */
    void onShowMore(); // 对 footer 操作
}
