package com.sleticalboy.doup.module.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sleticalboy.base.BaseFragment;
import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsFragment extends BaseFragment implements IBaseView,
        RecyclerArrayAdapter.OnItemClickListener,
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsFragment";

    @BindView(R.id.rv_news)
    EasyRecyclerView rvNews;

    private NewsPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        mPresenter = new NewsPresenter(getActivity(), this);
        mPresenter.initRecyclerView();
        mPresenter.initData();
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        rvNews.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerArrayAdapter adapter) {
        adapter.setError(R.layout.layout_error)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setMore(R.layout.layout_more, this);
        adapter.setNoMore(R.layout.layout_no_more)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setOnItemClickListener(this);
        rvNews.setAdapterWithProgress(adapter);
        rvNews.setRefreshListener(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.news_frag_main;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
        rvNews.setRefreshing(false);
    }

    @Override
    public void onNetError() {
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.clickItem(position);
    }

    public void scrollToPosition(int position) {
        rvNews.scrollToPosition(position);
    }

    /**
     * 显示新闻详情
     *
     * @param newsId 新闻 id
     */
    public void showNewsDetail(int newsId) {
        NewsDetailActivity.actionStart(getActivity(), newsId);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadMore(true);
    }

    @OnClick(R.id.fab_top)
    public void onViewClicked() {
        scrollToPosition(0);
    }
}
