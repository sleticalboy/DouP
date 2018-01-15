package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.RecommendBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.myrecyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class RecommendAdapter extends RecyclerArrayAdapter<RecommendBean.IssueListBean.ItemListBean> {

    public RecommendAdapter(Context context, List<RecommendBean.IssueListBean.ItemListBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<RecommendBean.IssueListBean.ItemListBean> {

        ImageView imgRecommend;
        ImageView imgUserIcon;
        TextView tvTitle;
        TextView tvDetail;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_recommend);
            imgRecommend = dollar(R.id.img_recommend);
            imgUserIcon = dollar(R.id.img_user_icon);
            tvTitle = dollar(R.id.tv_title);
            tvDetail = dollar(R.id.tv_detail);
        }

        @Override
        public void setData(RecommendBean.IssueListBean.ItemListBean data) {
            super.setData(data);
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
