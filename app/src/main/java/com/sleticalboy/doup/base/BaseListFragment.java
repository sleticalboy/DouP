package com.sleticalboy.doup.base;

import android.support.v4.widget.SwipeRefreshLayout;

import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public abstract class BaseListFragment extends LazyFragment implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener {

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
    public void onItemClick(int position) {

    }
}
