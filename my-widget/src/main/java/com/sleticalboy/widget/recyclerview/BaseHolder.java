package com.sleticalboy.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(View.inflate(parent.getContext(), viewId, null));
    }

    /**
     * 刷新数据
     *
     * @param data
     * @param position
     */
    public void refreshData(T data, int position) {
    }
}
