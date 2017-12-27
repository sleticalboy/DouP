package com.sleticalboy.doup.fragment.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.NewsListAdapter;
import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.http.ApiFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;

    private int mLastVisibleItemPosition;
    private String mDate;
    private NewsListAdapter mAdapter;
    private boolean mIsLoadMore = false;
    private NewsBean mNewsBean;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_news, null);
        ButterKnife.bind(this, rootView);

        Log.d(TAG, "init data");
        initData();

        mLayoutManager = new LinearLayoutManager(getContext());
        rvNews.setLayoutManager(mLayoutManager);

        mAdapter = new NewsListAdapter(getContext());
        rvNews.setAdapter(mAdapter);

        return rootView;
    }

    // 初始化数据
    private void initData() {
        // 获取最新新闻列表
        ApiFactory.getNewsApi().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    Log.d(TAG, newsBean.stories.get(0).title);
                    // 展示数据
                    showNewsList(newsBean);
                }, tr -> {
                    tr.printStackTrace();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                });
    }

    // 展示新闻列表
    private void showNewsList(NewsBean newsBean) {
        if (mIsLoadMore) {
            if (TextUtils.isEmpty(mDate)) {
                mAdapter.updateStatus(NewsListAdapter.LOAD_NONE);
                srl.setRefreshing(false);
                return;
            } else {
                mNewsBean.stories.addAll(newsBean.stories);
            }
        } else {
            mNewsBean = newsBean;
            // 将数据设置给 adapter
            mAdapter.setData(mNewsBean);
        }
        mAdapter.notifyDataSetChanged();
        mDate = newsBean.date;

        scrollRecyclerView();

        setSwipeRefreshLayout();

        setFloatingActionButton();
    }

    private void setSwipeRefreshLayout() {
        srl.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        srl.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                getResources().getDisplayMetrics()));
        srl.setOnRefreshListener(() -> {
            mAdapter.notifyDataSetChanged();
            srl.setRefreshing(false);
        });
    }

    @OnClick(R.id.fab_top)
    public void onViewClicked() {
        // 返回顶部
        Toast.makeText(getContext(), "返回顶部", Toast.LENGTH_SHORT).show();
        // 平滑滚动 RecyclerView 到顶部
//            mLayoutManager.smoothScrollToPosition(rvNews, null, 0);
        rvNews.scrollToPosition(0);
        fabTop.setVisibility(View.GONE);
    }

    private void setFloatingActionButton() {
        if (mLayoutManager.findLastVisibleItemPosition() >= 16) {
            fabTop.setVisibility(View.VISIBLE);
        } else {
            fabTop.setVisibility(View.GONE);
        }
    }

    private void scrollRecyclerView() {
        // 给 RecyclerView 添加滚动监听
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        mAdapter.updateStatus(NewsListAdapter.LOAD_NONE);
                        return;
                    }
                    if (mLastVisibleItemPosition + 1 == mLayoutManager.getItemCount()) {
                        mAdapter.updateStatus(NewsListAdapter.PULL_TO_LOAD);
                        mIsLoadMore = true;
                        mAdapter.updateStatus(NewsListAdapter.LOAD_MORE);
                        new Handler().postDelayed(() -> getOldNews(), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    // 上拉加载获取的数据，将获取的数据添加到已有的数据中，刷新 adapter
    private void getOldNews() {
        ApiFactory.getNewsApi().getOldNews(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    Log.d(TAG, newsBean.stories.get(0).title);
                    // 展示数据
                    showNewsList(newsBean);
                }, tr -> {
                    tr.printStackTrace();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                });
    }
}
