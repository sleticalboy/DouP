package com.sleticalboy.doup.module.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.NewsModel;
import com.sleticalboy.doup.model.news.NewsBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private String mDate = "20180117";

    public NewsPresenter(Context context, NewsFragment newsListView) {
        super(context);
        mNewsListView = newsListView;
        mNewsModel = new NewsModel(getContext());
    }

    /**
     * 初始化 RecyclerView
     */
    public void initRecyclerView() {
        mAdapter = new NewsListAdapter(getContext());
        mNewsListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsListView.setAdapter(mAdapter);
    }

    @Override
    public void setAdapter() {
    }

    @Override
    protected void setLayoutManager() {
    }

    /**
     * 初始化数据
     */
    public void initData() {
        mNewsModel.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    mDate = newsBean.date;
                    mAdapter.addAll(newsBean.stories);
                }, throwable -> {
                    throwable.printStackTrace();
                    mNewsListView.onNetError();
                });
    }

    /**
     * 加载更多数据
     *
     * @param isPullDown 是否是下拉加载
     */
    public void loadMore(boolean isPullDown) {
        mNewsModel.getOldNews(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    mDate = newsBean.date;
                    if (isPullDown) {
                        Log.d(TAG, "下拉加载");
                        // 1, 取出所有数据
                        List<NewsBean.StoriesBean> allData = mAdapter.getAllData();
                        // 2, 清空原有数据
                        mAdapter.clear();
                        // 3, 将新数据添加到原数据的前面
                        allData.addAll(0, newsBean.stories);
                        mAdapter.addAll(allData);
                        // 4, 刷新适配器
                        mAdapter.notifyDataSetChanged();
                        mNewsListView.onLoadingOver();
                    } else {
                        Log.d(TAG, "上拉加载");
                        // 直接将新数据追加到原有数据的尾部
                        mAdapter.addAll(newsBean.stories);
                        mAdapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mNewsListView.onNetError();
                });
    }

    public void clickItem(int position) {
        NewsBean.StoriesBean storiesBean = mAdapter.getItem(position);
        mNewsListView.showNewsDetail(storiesBean.id);
    }

    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mNewsModel.clear();
    }
}
