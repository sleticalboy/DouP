package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.HotBean;
import com.sleticalboy.util.CommonUtils;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.myrecyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class RankAdapter extends RecyclerArrayAdapter<HotBean.ItemListBean.DataBean> {

    public RankAdapter(Context context, List<HotBean.ItemListBean.DataBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankHolder(parent);
    }

    static class RankHolder extends BaseViewHolder<HotBean.ItemListBean.DataBean> {

        ImageView imgPhoto;
        TextView tvTitle;
        TextView tvTime;

        public RankHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_rank);
            imgPhoto = dollar(R.id.img_photo);
            tvTitle = dollar(R.id.tv_title);
            tvTime = dollar(R.id.tv_time);
        }

        @Override
        public void setData(HotBean.ItemListBean.DataBean data) {
            tvTitle.setText(data.title);
            ImageLoader.load(getContext(), imgPhoto, data.cover.feed);
            tvTime.setText(String.format("%s / %s", data.category, CommonUtils.wrapperTime(data.duration)));
        }
    }
}
