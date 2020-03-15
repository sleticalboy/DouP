package com.sleticalboy.doup.module.home;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class NewsListAdapterBase extends BaseRecyclerAdapter<NewsBean.StoriesBean> {

    private static final String TAG = "NewsListAdapterBase";

    public NewsListAdapterBase(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<NewsBean.StoriesBean> {

        CardView cardView;
        TextView newsTitle;
        ImageView newsImg;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.news_recycle_item_news);
            cardView = obtainView(R.id.card_view);
            newsTitle = obtainView(R.id.news_title);
            newsImg = obtainView(R.id.news_img);
        }

        @Override
        public void bindData(NewsBean.StoriesBean data) {
            Log.d(TAG, data.title);
            newsTitle.setText(data.title);
            ImageLoader.load(getContext(), newsImg, data.images.get(0));
        }
    }
}
