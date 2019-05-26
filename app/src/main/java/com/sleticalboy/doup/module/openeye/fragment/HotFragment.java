package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sleticalboy.base.IBaseView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class HotFragment extends LazyFragment implements IBaseView,
        SwipeRefreshLayout.OnRefreshListener,
        BaseRecyclerAdapter.OnItemClickListener {

//    @BindView(R.id.rv_popular)
//    EasyRecyclerView rvPopular;

    private HotPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
//        mPresenter = new HotPresenter(getActivity(), this);
//        mPresenter.initRecyclerView();

    }

    public void setLayoutManager() {
        //
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        //
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void fetchData() {
//        mPresenter.initData();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onLoadFinished() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
//        mPresenter.loadMore(true);
    }
}
