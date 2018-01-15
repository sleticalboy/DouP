package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */

public class FindingDetailActivity extends BaseActivity implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "FindingDetailActivity";

    public static final String NAME = "name";

    @BindView(R.id.rv_rank)
    EasyRecyclerView rvRank;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private String mName;
    private int mLastVisibleItemIndex;

    private RankPresenter mPresenter;

    @Override
    protected void prepareTask() {
        Intent intent = getIntent();
        if (intent != null) {
            mName = intent.getStringExtra(NAME);
        }
    }

    @Override
    protected void initView() {
        mPresenter = new RankPresenter(this, this);
        mPresenter.setLayoutManager();
        mPresenter.setAdapter();

        mPresenter.initData(mName);

        srl.setOnRefreshListener(this);

        rvRank.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mPresenter.getItemCount() == 1) return;
                    if (mPresenter.getItemCount() + 1 == mLastVisibleItemIndex) {
                        mPresenter.loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_finding_detail;
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, FindingDetailActivity.class);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
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
        return rvRank;
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

    }

    @Override
    public void onRefresh() {
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
            // 更新数据
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
        VideoPlayActivity.actionStart(this, videoBean);
    }
}
