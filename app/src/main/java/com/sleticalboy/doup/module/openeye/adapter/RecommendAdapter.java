package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.openeye.ItemListBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class RecommendAdapter extends RecyclerArrayAdapter<ItemListBean> {

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<ItemListBean> {

        ImageView imgRecommend;
        ImageView imgUserIcon;
        TextView tvTitle;
        TextView tvDetail;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.openeye_recycle_item_recommend);
            imgRecommend = obtainView(R.id.img_recommend);
            imgUserIcon = obtainView(R.id.img_user_icon);
            tvTitle = obtainView(R.id.tv_title);
            tvDetail = obtainView(R.id.tv_detail);
        }

        @Override
        public void setData(ItemListBean data) {
            ImageLoader.load(getContext(), imgRecommend, data.data.cover.feed);
            if (data.data.author != null) {
                ImageLoader.load(getContext(), imgUserIcon, data.data.author.icon);
            } else {
                imgUserIcon.setVisibility(View.GONE);
            }
            tvTitle.setText(data.data.title);
            tvDetail.setText(data.data.description);
        }
    }
}
