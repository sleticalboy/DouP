package com.sleticalboy.doup.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.ToastUtils;

import io.reactivex.Observable;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public abstract class BaseListFragment<T extends IBasePresenter> extends LazyLoadFragment<T>
        implements IBaseListView<T>, SwipeRefreshLayout.OnRefreshListener {

    protected MultiTypeAdapter mAdapter;
    protected Items oldItems = new Items();
    protected boolean mCanLoadMore = false;
    protected Observable<Integer> mObservable;

    @Override
    public void onRefresh() {

    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHiddenLoading() {

    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(getActivity(), "网络不给力");
        getActivity().runOnUiThread(() -> {
            mAdapter.setItems(new Items());
            mAdapter.notifyDataSetChanged();
            mCanLoadMore = false;
        });
    }

    @Override
    public void onShowNoMore() {
        getActivity().runOnUiThread(() -> {
            if (oldItems.size() > 0) {
                Items newItems = new Items(oldItems);
                newItems.remove(newItems.size() - 1);
                newItems.add(new Object()); // 加载完成
                mAdapter.setItems(newItems);
            } else {
                oldItems.add(new Object()); // 加载完成
                mAdapter.setItems(oldItems);
            }
            mAdapter.notifyDataSetChanged();
            mCanLoadMore = false;
        });
    }

    @Override
    protected void fetchData() {
        mObservable = RxBus.getBus().register(TAG);
        mObservable.subscribe(integer -> mAdapter.notifyDataSetChanged());
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View rootView) {
        // 初始化控件
    }

    @Override
    protected int attachLayout() {
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 设置下拉刷新的按钮的颜色
    }

    @Override
    public void onDestroy() {
        RxBus.getBus().unregister(TAG, mObservable);
        super.onDestroy();
    }
}
