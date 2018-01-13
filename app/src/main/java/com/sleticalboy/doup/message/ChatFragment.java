package com.sleticalboy.doup.message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.IBaseListView;
import com.sleticalboy.doup.base.LazyFragment;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/12/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class ChatFragment extends LazyFragment implements IBaseListView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "ChatFragment";

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingEnd() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onRefresh() {

    }
}
