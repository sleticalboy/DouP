package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.activity.VideoPlayActivity;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class RecommendFragment extends LazyFragment implements IRecommendView,
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "RecommendFragment";

    @BindView(R.id.rv_recommend)
    EasyRecyclerView rvRecommend;

    private RecommendPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        mPresenter = new RecommendPresenter(getActivity(), this);
        mPresenter.initRecyclerView();
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_recommond;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
        rvRecommend.setRefreshing(false);
    }

    @Override
    public void onNetError() {
    }

    @Override
    protected void fetchData() {
        mPresenter.initData();
    }

    @Override
    public void onRefresh() {
        mPresenter.loadMore(true);
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.clickItem(position);
    }

    public void showVideoDetail(VideoBean videoBean) {
        VideoPlayActivity.actionStart(getActivity(), videoBean);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(false);
    }

    public void setLayoutManager() {
        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setAdapter(RecyclerArrayAdapter adapter) {
        adapter.setError(R.layout.layout_error)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setMore(R.layout.layout_more, this);
        adapter.setNoMore(R.layout.layout_no_more)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setOnItemClickListener(this);
        rvRecommend.setAdapterWithProgress(adapter);
        rvRecommend.setRefreshListener(this);
    }
}
