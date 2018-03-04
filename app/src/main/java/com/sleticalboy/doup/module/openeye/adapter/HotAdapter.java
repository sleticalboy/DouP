package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.sleticalboy.doup.bean.openeye.DataBean;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class HotAdapter extends RecyclerArrayAdapter<DataBean> {

    public HotAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<DataBean> {
        public ViewHolder(ViewGroup itemView) {
            super(itemView, -1);
        }

        @Override
        public void setData(DataBean data) {

        }
    }
}
