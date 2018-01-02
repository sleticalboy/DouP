package com.sleticalboy.doup.adapter.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.activity.NewsDetailActivity;
import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.util.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewsListAdapter";
    private static final int TYPE_NEWS = 0x000;
    private static final int TYPE_BANNER = 0x001;

    public static final int LOAD_MORE = 0x003;
    public static final int PULL_TO_LOAD = 0x004;
    public static final int LOAD_NONE = 0x005;
    public static final int LOAD_END = 0x006;

    private int mStatus = PULL_TO_LOAD; // 用于控制显示加载文字

    private Context mContext;
    private NewsBean mData;

    public NewsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        // TODO: 12/25/17 开启定时任务:顶部轮播图
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        // TODO: 12/25/17 关闭定时任务
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newsItem = View.inflate(parent.getContext(), R.layout.item_news, null);
        return new NewsViewHolder(newsItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsHolder = (NewsViewHolder) holder;
            NewsBean.StoriesBean storiesBean = mData.stories.get(position);
            newsHolder.cardView.setOnClickListener(v -> {
                // 新闻详情页面
                NewsDetailActivity.actionStart(mContext, storiesBean.id);
            });
            newsHolder.newsTitle.setText(storiesBean.title);
            ImageLoader.load(mContext, newsHolder.newsImg, storiesBean.images.get(0));
        } else if (holder instanceof BannerViewHolder) {
            // TODO: 12/25/17 绑定顶部轮播图
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.stories.size();
    }

    public void setData(NewsBean data) {
        mData = data;
    }

    public void updateStatus(int status) {
        mStatus = status;
        notifyDataSetChanged();
    }

    // 新闻 item
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_img)
        ImageView newsImg;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // 顶部轮播图 item
    static class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
