package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.activity.VideoPlayActivity;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class RecommendFragment extends LazyFragment implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "RecommendFragment";

    @BindView(R.id.rv_recommend)
    EasyRecyclerView rvRecommend;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private int mLastVisibleItemIndex;
    private RecommendPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        mPresenter = new RecommendPresenter(getActivity(), this);
        mPresenter.setLayoutManager();
        mPresenter.setAdapter();

        rvRecommend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mPresenter.getItemCount() == 1) {
                        return;
                    }
                    if (mPresenter.getItemCount() + 1 == mLastVisibleItemIndex) {
                        // 上拉加载更多数据
                        mPresenter.loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
            }
        });

        srl.setOnRefreshListener(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_recommond;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingOver() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return rvRecommend;
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

    }

    @Override
    protected void fetchData() {
        mPresenter.initData();
    }

    @Override
    public void onRefresh() {
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
            mPresenter.loadMore(true);
        } else {
            srl.setRefreshing(true);
        }
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.onItemClick(position);
    }

    public void showVideoDetail(VideoBean videoBean) {
        VideoPlayActivity.actionStart(getActivity(), videoBean);
    }
}
