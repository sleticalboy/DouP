package com.sleticalboy.doup.fragment.meizi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.MeiziAdapter;
import com.sleticalboy.doup.bean.meizi.BeautyBean;
import com.sleticalboy.doup.http.ApiFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class MeiziFragment extends Fragment {

    private static final String TAG = "MeiziFragment";

    @BindView(R.id.rv_meizi)
    RecyclerView rvMeizi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private BeautyBean mLocalData;
    private GridLayoutManager mLayoutManager;
    private MeiziAdapter mAdapter;
    private int mLastVisibleItemIndex;
    private boolean mIsLoadMore = false;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_meizi, container, false);
        ButterKnife.bind(this, rootView);

        initData();

        return rootView;
    }

    private void initData() {
        ApiFactory.getMeiziApi().getBeauty(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMeiziList, Throwable::printStackTrace);
    }

    private void showMeiziList(BeautyBean beautyBean) {

        if (!mIsLoadMore) {
            mLocalData = beautyBean;
        }

        Log.d(TAG, "onCreateView = set recyclerView");
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMeizi.setLayoutManager(mLayoutManager);
        mAdapter = new MeiziAdapter(getContext());
        rvMeizi.setAdapter(mAdapter);

        Log.d(TAG, "onCreateView = adapter set data");
        mAdapter.setData(mLocalData);
        mAdapter.notifyDataSetChanged();

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
                pullDownLoadMore();
            }
        });

        rvMeizi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(TAG, "newState:" + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        // 第一条数据
                        return;
                    }
                    Log.d(TAG, "mLayoutManager.getItemCount():" + mLayoutManager.getItemCount());
                    Log.d(TAG, "mLastVisibleItemIndex:" + mLastVisibleItemIndex);
                    if (mLayoutManager.getItemCount() + 1 == mLastVisibleItemIndex) {
                        // 最后一条数据， 加载更多数据
                        mIsLoadMore = true;
                        Log.d(TAG, "mIsLoadMore:" + mIsLoadMore);
                        pullUpLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    // 上拉加载数据
    private void pullUpLoadMore() {
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(aLong -> {
                    page += 1;
                    ApiFactory.getMeiziApi().getBeauty(page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(beautyBean -> {
                                mLocalData.results.addAll(beautyBean.results);
                                mAdapter.notifyDataSetChanged();
                            }, Throwable::printStackTrace);
                    return null;
                })
                .subscribe();
    }

    // 下拉加载数据
    private void pullDownLoadMore() {
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(aLong -> {
                    srl.setRefreshing(true); // 显示刷新
                    // 加载数据
                    page += 1;
                    ApiFactory.getMeiziApi().getBeauty(page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(beauty -> {
                                mLocalData.results.addAll(0, beauty.results);
                                mAdapter.notifyDataSetChanged();
                            }, Throwable::printStackTrace);
                    srl.setRefreshing(false); // 隐藏刷新
                    return null;
                })
                .subscribe();
    }
}
