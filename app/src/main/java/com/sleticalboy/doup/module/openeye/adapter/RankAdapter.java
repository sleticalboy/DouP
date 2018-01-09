package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.activity.VideoPlayActivity;
import com.sleticalboy.doup.model.openeye.PopularBean;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.util.CommonUtils;
import com.sleticalboy.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankHolder> {

    private Context mContext;
    private List<PopularBean.ItemListBean.DataBean> mDataList;

    public RankAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(mContext, R.layout.item_rank, null);
        return new RankHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        PopularBean.ItemListBean.DataBean data = mDataList.get(position);

        holder.tvTitle.setText(data.title);
        ImageLoader.load(mContext, holder.imgPhoto, data.cover.feed);
        holder.tvTime.setText(String.format("%s / %s", data.category, CommonUtils.wrapperTime(data.duration)));

        holder.itemView.setOnClickListener(v -> {
            VideoBean videoBean = wrapperVideo(data);
            VideoPlayActivity.actionStart(mContext, videoBean);
        });
    }

    private VideoBean wrapperVideo(PopularBean.ItemListBean.DataBean data) {
        if (data != null) {
            VideoBean videoBean = new VideoBean();
            videoBean.title = data.title;
            videoBean.playUrl = data.playUrl;
            videoBean.category = data.category;
            videoBean.desc = data.description;
            videoBean.feed = data.cover.feed;
            videoBean.time = System.currentTimeMillis();
            videoBean.duration = data.duration;
            videoBean.blurred = data.cover.blurred;
            videoBean.shareCount = data.consumption.shareCount;
            videoBean.replyCount = data.consumption.replyCount;
            videoBean.collectCount = data.consumption.collectionCount;
            return videoBean;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setDataList(List<PopularBean.ItemListBean.DataBean> dataList) {
        mDataList = dataList;
    }

    static class RankHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_text)
        RelativeLayout rlText;

        public RankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
