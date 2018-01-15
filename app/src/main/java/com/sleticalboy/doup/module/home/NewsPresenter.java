package com.sleticalboy.doup.module.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.NewsModel;
import com.sleticalboy.doup.model.news.NewsBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/11/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class NewsPresenter extends BasePresenter {

    public static final String TAG = "NewsPresenter";

    private NewsFragment mNewsListView;
    private NewsModel mNewsModel;
    private NewsListAdapter mAdapter;
    private List<NewsBean.StoriesBean> mData = new ArrayList<>();
    private String mDate;
    private LinearLayoutManager mLayoutManager;

    public NewsPresenter(Context context, NewsFragment newsListView) {
        super(context);
        mNewsListView = newsListView;
        mNewsModel = new NewsModel(getContext());
        initData();
    }

    @Override
    public void setAdapter() {
        mAdapter = new NewsListAdapter(getContext(), mData);
        mNewsListView.getRecyclerView().setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mNewsListView);
    }

    @Override
    protected void setLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mNewsListView.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    // 初始化数据
    private void initData() {
        // 获取最新新闻列表
        mNewsListView.onLoading();
        mNewsModel.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    Log.d(TAG, "第一次初始化数据");
                    mDate = newsBean.date;
                    mData.addAll(newsBean.stories);
                    mAdapter.addAll(mData);
                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, newsBean.stories.get(0).title);
                    mNewsListView.onLoadingOver();
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mNewsListView.onNetError();
                    }
                });
    }

    public void loadMore(boolean isPullDown) {
        mNewsListView.onShowMore();
        mNewsModel.getOldNews(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    if (newsBean.stories.size() == 0) {
                        mNewsListView.onNoMore();
                    }
                    mAdapter.clear();
                    if (isPullDown) { // 下拉加载
                        Log.d(TAG, "下拉加载");
                        mData.addAll(0, newsBean.stories);
                        mAdapter.addAll(mData);
                        mAdapter.notifyDataSetChanged();
                        mNewsListView.onLoadMoreOver();
                    } else { // 上拉加载
                        Log.d(TAG, "上拉加载");
                        mData.addAll(newsBean.stories);
                        mAdapter.addAll(mData);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mNewsListView.onNetError();
                    }
                });
    }

    public void chooseNewsItem(int position) {
        NewsBean.StoriesBean storiesBean = mData.get(position);
        mNewsListView.showNewsDetail(storiesBean.id);
    }

    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mNewsModel.clear();
    }

    public int findLastVisibleItemPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    public int getItemCount() {
        return mLayoutManager.getItemCount();
    }
}
