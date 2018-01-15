package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class HotFragment extends LazyFragment implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.rv_popular)
    EasyRecyclerView rvPopular;

    private HotPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
//        mPresenter = new HotPresenter(getActivity(), this);
//
//        mPresenter.setLayoutManager();
//        mPresenter.setAdapter();

        srl.setOnRefreshListener(this);
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_popular;
    }

    @Override
    protected void fetchData() {
//        mPresenter.initData();
    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return rvPopular;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

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
    public void onItemClick(int position) {

    }
}
