package com.sleticalboy.doup.module.home;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseFragment;
import com.sleticalboy.doup.base.IBaseListView;
import com.sleticalboy.util.ToastUtils;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsFragment extends BaseFragment implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "NewsFragment";

    @BindView(R.id.rv_news)
    EasyRecyclerView rvNews;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private int mLastVisibleItemPosition;
    private NewsPresenter mPresenter;
    private ProgressDialog mDialog;

    @Override
    protected void initView(View rootView) {
        mPresenter = new NewsPresenter(getActivity(), this);
        mPresenter.setAdapter();
        setOnScrollListener();
        setSwipeRefreshLayout();
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_news;
    }

    private void setSwipeRefreshLayout() {
        srl.setColorSchemeResources(
                R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int end = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, displayMetrics);
        srl.setProgressViewOffset(true, 0, end);
    }

    private void setOnScrollListener() {
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemPosition = mPresenter.findLastVisibleItemPosition();
                    if (mPresenter.getItemCount() == 1) {
                        return;
                    }
                    if (mLastVisibleItemPosition + 1 == mPresenter.getItemCount()) {
                        mPresenter.loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mPresenter.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onLoading() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("正在加载");
            mDialog.setCancelable(false);
        } else {
            if (!mDialog.isShowing())
                mDialog.show();
        }
    }

    @Override
    public void onLoadingEnd() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(getActivity(), "网络不见啦");
    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return rvNews;
    }

    @Override
    public void onNoMore() {
        ToastUtils.showToast(getActivity(), "没有更多数据了");
    }

    @Override
    public void onShowMore() {
        ToastUtils.showToast(getActivity(), "加载更多数据。。。");
    }

    @Override
    public void onRefresh() {
        // FIXME: 1/12/18 下拉加载不生效，需要修复
        if (srl.isRefreshing()) {
            mPresenter.loadMore(true);
        }
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.chooseNewsItem(position);
    }

    /**
     * 显示新闻详情
     *
     * @param newsId 新闻 id
     */
    public void showNewsDetail(int newsId) {
        NewsDetailActivity.actionStart(getActivity(), newsId);
    }

    public void onLoadMoreOver() {
        srl.setRefreshing(false);
    }
}
