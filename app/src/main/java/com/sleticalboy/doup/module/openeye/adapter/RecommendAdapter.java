package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.activity.VideoPlayActivity;
import com.sleticalboy.doup.model.openeye.RecommendBean;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context mContext;

    private List<RecommendBean.IssueListBean.ItemListBean> mData;

    public RecommendAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_recommend, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecommendBean.IssueListBean.ItemListBean itemData = mData.get(position);

        ImageLoader.load(mContext, holder.imgRecommend, itemData.data.cover.feed);
        if (itemData.data.author != null) {
            ImageLoader.load(mContext, holder.imgUserIcon, itemData.data.author.icon);
        } else {
            holder.imgUserIcon.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(itemData.data.title);
        holder.tvDetail.setText(itemData.data.description);

        VideoBean videoBean = wrapperVideo(itemData);
        holder.itemView.setOnClickListener(v -> VideoPlayActivity.actionStart(mContext, videoBean));

    }

    private VideoBean wrapperVideo(RecommendBean.IssueListBean.ItemListBean itemData) {
        if (itemData != null) {
            VideoBean videoBean = new VideoBean();
            videoBean.title = itemData.data.title;
            videoBean.playUrl = itemData.data.playUrl;
            videoBean.category = itemData.data.category;
            videoBean.desc = itemData.data.description;
            videoBean.feed = itemData.data.cover.feed;
            videoBean.time = System.currentTimeMillis();
            videoBean.duration = itemData.data.duration;
            videoBean.blurred = itemData.data.cover.blurred;
            videoBean.shareCount = itemData.data.consumption.shareCount;
            videoBean.replyCount = itemData.data.consumption.replyCount;
            videoBean.collectCount = itemData.data.consumption.collectionCount;
            return videoBean;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_recommend)
        ImageView imgRecommend;
        @BindView(R.id.img_user_icon)
        ImageView imgUserIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_detail)
        TextView tvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(List<RecommendBean.IssueListBean.ItemListBean> data) {
        mData = data;
    }
}
