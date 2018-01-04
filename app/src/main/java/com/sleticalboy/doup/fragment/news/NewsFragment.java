package com.sleticalboy.doup.fragment.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.news.NewsListAdapter;
import com.sleticalboy.doup.mvp.model.NewsModel;
import com.sleticalboy.doup.mvp.model.bean.news.NewsBean;
import com.sleticalboy.doup.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private NewsBean mLocalData;
    private LinearLayoutManager mLayoutManager;

    private NewsModel mNewsModel;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_news, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mNewsModel = new NewsModel(getActivity());

        initView();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvNews.setLayoutManager(mLayoutManager);

        mAdapter = new NewsListAdapter(getActivity());
        rvNews.setAdapter(mAdapter);

        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        return;
                    }
                    if (mLastVisibleItemPosition + 1 == mLayoutManager.getItemCount()) {
                        mIsLoadMore = true;
                        loadMore(false);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        setSwipeRefreshLayout();

        setFloatingActionButton();
    }

    // 初始化数据
    private void initData() {
        // 获取最新新闻列表
        mNewsModel.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    Log.d(TAG, "初始化数据");
                    mDate = newsBean.date;
                    if (!mIsLoadMore) {
                        mLocalData = newsBean;
                        mAdapter.setData(mLocalData);
                        mAdapter.notifyDataSetChanged();
                    }
                    Log.d(TAG, mLocalData.stories.get(0).title);
                }, tr -> {
                    tr.printStackTrace();
                    ToastUtils.showToast(getContext(), "加载失败");
                });
    }

    private void setSwipeRefreshLayout() {
        srl.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        srl.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                getResources().getDisplayMetrics()));
        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                loadMore(true);
                mAdapter.notifyDataSetChanged();
                srl.setRefreshing(false);
            }
        });
    }

    private void setFloatingActionButton() {
        ToastUtils.showToast(getContext(), "返回顶部");
        if (mLastVisibleItemPosition >= 16) {
            fabTop.setVisibility(View.VISIBLE);
        } else {
            fabTop.setVisibility(View.GONE);
        }
        rvNews.scrollToPosition(0);
        fabTop.setVisibility(View.GONE);
    }

    /**
     * 加载更多数据
     *
     * @param isPullDown true 表示下拉加载， false 表示上拉加载
     */
    private void loadMore(boolean isPullDown) {
        new Handler().postDelayed(() -> mNewsModel.getOldNews(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsBean -> {
                    Log.d(TAG, "date = " + newsBean.date);
                    mDate = newsBean.date;
                    if (isPullDown) { // 下拉加载
                        Log.d(TAG, "下拉加载");
                        mLocalData.stories.addAll(0, newsBean.stories);
                    } else { // 上拉加载
                        Log.d(TAG, "上拉加载");
                        mLocalData.stories.addAll(newsBean.stories);
                    }
                    Log.d(TAG, mLocalData.stories.get(0).title);
                    mAdapter.notifyDataSetChanged();
                }, tr -> {
                    tr.printStackTrace();
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                }), 1000);
    }
}
