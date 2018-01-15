package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.sleticalboy.doup.model.openeye.HotBean;
import com.sleticalboy.widget.myrecyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class HotAdapter extends RecyclerArrayAdapter<HotBean.ItemListBean.DataBean> {

    public HotAdapter(Context context, List<HotBean.ItemListBean.DataBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    static class ViewHolder extends BaseViewHolder<HotBean.ItemListBean.DataBean> {
        public ViewHolder(ViewGroup itemView) {
            super(itemView, -1);
        }
    }
}
