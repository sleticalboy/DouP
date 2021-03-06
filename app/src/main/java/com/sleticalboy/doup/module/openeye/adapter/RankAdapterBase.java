package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.openeye.DataBean;
import com.sleticalboy.util.CommonUtils;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class RankAdapterBase extends BaseRecyclerAdapter<DataBean> {

    public RankAdapterBase(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankHolder(parent);
    }

    static class RankHolder extends BaseViewHolder<DataBean> {

        ImageView imgPhoto;
        TextView tvTitle;
        TextView tvTime;

        public RankHolder(ViewGroup itemView) {
            super(itemView, R.layout.openeye_recycle_item_rank);
            imgPhoto = obtainView(R.id.img_photo);
            tvTitle = obtainView(R.id.tv_title);
            tvTime = obtainView(R.id.tv_time);
        }

        @Override
        public void bindData(DataBean data) {
            tvTitle.setText(data.title);
            ImageLoader.load(getContext(), imgPhoto, data.cover.feed);
            tvTime.setText(String.format("%s / %s", data.category, CommonUtils.wrapperTime(data.duration)));
        }
    }
}
