package com.sleticalboy.doup.message.jpush;

import android.content.Context;
import android.view.ViewGroup;

import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class PushMsgAdapter extends RecyclerArrayAdapter<PushMsgBean> {

    public PushMsgAdapter(Context context) {
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
        public void setData(PushMsgBean data) {

        }
    }
}
