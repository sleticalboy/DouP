package com.sleticalboy.doup.message.jpush;

import android.content.Context;
import android.view.ViewGroup;

import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class PushMsgAdapterBase extends BaseRecyclerAdapter<PushMsgBean> {

    public PushMsgAdapterBase(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    private static class ViewHolder extends BaseViewHolder<PushMsgBean> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_push);
        }

        @Override
        public void bindData(PushMsgBean data) {

        }
    }
}
