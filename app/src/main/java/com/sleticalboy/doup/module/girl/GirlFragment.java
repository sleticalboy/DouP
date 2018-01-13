package com.sleticalboy.doup.module.girl;

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
import com.sleticalboy.doup.model.GirlModel;
import com.sleticalboy.doup.model.girl.GirlBean;
import com.sleticalboy.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class GirlFragment extends Fragment {

    private static final String TAG = "GirlFragment";

    @BindView(R.id.rv_meizi)
    RecyclerView rvMeizi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private GirlBean mLocalData;
    private GridLayoutManager mLayoutManager;
    private GirlAdapter mAdapter;
    private int mLastVisibleItemIndex;
    private boolean mIsLoadMore = false;
    private int page = 1;

    private GirlModel mGirlModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_meizi, container, false);
        ButterKnife.bind(this, rootView);

        mGirlModel = new GirlModel(getContext());

        initView();

        initData();

        return rootView;
    }

    private void initView() {

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMeizi.setLayoutManager(mLayoutManager);
        mAdapter = new GirlAdapter(getContext());
        rvMeizi.setAdapter(mAdapter);

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                loadMore(true);
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
                    LogUtils.d(TAG, "mLayoutManager.getItemCount():" + mLayoutManager.getItemCount());
                    LogUtils.d(TAG, "mLastVisibleItemIndex:" + mLastVisibleItemIndex);
                    if (mLayoutManager.getItemCount() + 1 == mLastVisibleItemIndex) {
                        // 最后一条数据， 加载更多数据
                        mIsLoadMore = true;
                        loadMore(false);
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

    private void initData() {
        mGirlModel.getMeizi(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    if (!mIsLoadMore) {
                        mLocalData = girlBean;
                    }
                    mAdapter.setData(mLocalData);
                    mAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);
    }

    private void loadMore(boolean isPullDown) {
        page += 1;
        mGirlModel.getMeizi(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    if (isPullDown) {
                        mLocalData.results.addAll(0, girlBean.results);
                    } else {
                        mLocalData.results.addAll(girlBean.results);
                    }
                    mAdapter.notifyDataSetChanged();
                    srl.setRefreshing(false);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGirlModel.clear();
    }
}
