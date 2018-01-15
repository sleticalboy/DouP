package com.sleticalboy.doup.module.girl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseFragment;
import com.sleticalboy.doup.base.IBaseListView;
import com.sleticalboy.util.LogUtils;
import com.sleticalboy.util.ToastUtils;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class GirlFragment extends BaseFragment implements IBaseListView {

    private static final String TAG = "GirlFragment";

    @BindView(R.id.rv_meizi)
    EasyRecyclerView rvMeizi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private int mLastVisibleItemIndex;
    GirlListPresenter mPresenter;

    @Override
    protected int attachLayout() {
        return R.layout.frag_meizi;
    }

    @Override
    protected void initView(View rootView) {
        mPresenter = new GirlListPresenter(getActivity(), this);
        mPresenter.setLayoutManager();
        mPresenter.setAdapter();

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                mPresenter.loadMore(true);
            }
        });

        rvMeizi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(TAG, "newState:" + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
                    if (mPresenter.getItemCount() == 1) {
                        // 第一条数据
                        return;
                    }
                    LogUtils.d(TAG, "mLayoutManager.getItemCount():" + mPresenter.getItemCount());
                    LogUtils.d(TAG, "mLastVisibleItemIndex:" + mLastVisibleItemIndex);
                    if (mPresenter.getItemCount() + 1 == mLastVisibleItemIndex) {
                        // 最后一条数据， 加载更多数据
                        mPresenter.loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemIndex = mPresenter.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingOver() {
        srl.setRefreshing(false);
    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(getActivity(), "网络不见啦");
    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return rvMeizi;
    }

    @Override
    public void onNoMore() {
        ToastUtils.showToast(getActivity(), "没有更多数据了");
    }

    @Override
    public void onShowMore() {
        ToastUtils.showToast(getActivity(), "加载更多数据。。。");
    }
}
