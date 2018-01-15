package com.sleticalboy.doup.module.home;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.news.NewsBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.myrecyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class NewsListAdapter extends RecyclerArrayAdapter<NewsBean.StoriesBean> {

    private static final String TAG = "NewsListAdapter";

    public NewsListAdapter(Context context, List<NewsBean.StoriesBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }

    // 新闻 item
    static class NewsViewHolder extends BaseViewHolder<NewsBean.StoriesBean> {

        CardView cardView;
        TextView newsTitle;
        ImageView newsImg;

        public NewsViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_news);
            cardView = dollar(R.id.card_view);
            newsTitle = dollar(R.id.news_title);
            newsImg = dollar(R.id.news_img);
        }

        @Override
        public void setData(NewsBean.StoriesBean data) {
            Log.d(TAG, data.title);
            newsTitle.setText(data.title);
            ImageLoader.load(getContext(), newsImg, data.images.get(0));
        }
    }

    // 顶部轮播图 item
    class BannerViewHolder extends BaseViewHolder<NewsBean> {

        public BannerViewHolder(ViewGroup itemView) {
            super(itemView);
        }

        @Override
        public void setData(NewsBean newsBean) {
            NewsBean.TopStoriesBean data = newsBean.top_stories.get(getAdapterPosition());
            Log.d(TAG, data.title);
        }
    }
}
