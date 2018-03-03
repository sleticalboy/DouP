package com.sleticalboy.doup.module.girl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.sleticalboy.base.BaseFragment;
import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.R;
import com.sleticalboy.util.ToastUtils;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class GirlFragment extends BaseFragment implements IBaseView,
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GirlFragment";

    @BindView(R.id.rv_meizi)
    EasyRecyclerView rvMeizi;

    GirlListPresenter mPresenter;

    @Override
    protected int attachLayout() {
        return R.layout.girl_frag_main;
    }

    @Override
    protected void initView(View rootView) {
        mPresenter = new GirlListPresenter(getActivity(), this);
        mPresenter.initRecyclerView();
        mPresenter.initData();
    }

    public void setLayoutManager() {
        rvMeizi.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    public void setAdapter(RecyclerArrayAdapter adapter) {
        adapter.setError(R.layout.layout_error)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setMore(R.layout.layout_more, this);
        adapter.setNoMore(R.layout.layout_no_more)
                .setOnClickListener(v -> adapter.resumeMore());
        rvMeizi.setAdapterWithProgress(adapter);
        rvMeizi.setRefreshListener(this);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
        rvMeizi.setRefreshing(false);
    }

    @Override
    public void onNetError() {
        ToastUtils.INSTANCE.showToast(getActivity(), "网络不见啦");
    }

    @Override
    public void onRefresh() {
        mPresenter.loadMore(true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(false);
    }
}
