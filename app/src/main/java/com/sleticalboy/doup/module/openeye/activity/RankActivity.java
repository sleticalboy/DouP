package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.fragment.IRecommendView;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */
public class RankActivity extends BaseActivity implements IRecommendView,
        SwipeRefreshLayout.OnRefreshListener,
        BaseRecyclerAdapter.OnLoadMoreListener,
        BaseRecyclerAdapter.OnItemClickListener {

    private static final String TAG = "RankActivity";

    public static final String NAME = "name";

    @BindView(R.id.rv_rank)
    EasyRecyclerView rvRank;

    private String mName;

    private RankPresenter mPresenter;

    @Override
    protected void beforeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            mName = intent.getStringExtra(NAME);
            Log.d(TAG, mName);
        }
    }

    @Override
    protected void initView(final Bundle savedInstanceState) {
        mPresenter = new RankPresenter(this, this);
        mPresenter.initRecyclerView();
        mPresenter.initData(mName);
    }

    @Override
    public void setLayoutManager() {
        rvRank.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        adapter.setError(R.layout.layout_error)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setMore(R.layout.layout_more, this);
        adapter.setNoMore(R.layout.layout_no_more)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setOnItemClickListener(this);
        rvRank.setAdapterWithProgress(adapter);
        rvRank.setRefreshListener(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.openeye_activity_finding_detail;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
        rvRank.setRefreshing(false);
    }

    @Override
    public void onNetError() {
    }

    @Override
    public void onRefresh() {
        mPresenter.loadMore(true);
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.clickItem(position);
    }

    @Override
    public void showVideoDetail(VideoBean videoBean) {
        VideoPlayActivity.actionStart(this, videoBean);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(false);
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, RankActivity.class);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }
}
