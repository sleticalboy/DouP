package com.sleticalboy.widget.recyclerview.adapter;

import android.view.View;

/**
 * Created on 2015/8/18.
 * @author Mr.Jude
 * @version 1.0
 * @description
 */
public interface EventDelegate {

    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, BaseRecyclerAdapter.OnMoreListener listener);
    void setNoMore(View view, BaseRecyclerAdapter.OnNoMoreListener listener);
    void setErrorMore(View view, BaseRecyclerAdapter.OnErrorListener listener);
    void setMore(int res, BaseRecyclerAdapter.OnMoreListener listener);
    void setNoMore(int res, BaseRecyclerAdapter.OnNoMoreListener listener);
    void setErrorMore(int res, BaseRecyclerAdapter.OnErrorListener listener);
}
