package com.sleticalboy.doup.fragment.meizi;

import android.os.Bundle;
import android.os.Handler;
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
import com.sleticalboy.doup.adapter.meizi.MeiziAdapter;
import com.sleticalboy.doup.mvp.model.MeiziModel;
import com.sleticalboy.doup.mvp.model.bean.meizi.BeautyBean;
import com.sleticalboy.doup.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private MeiziModel mMeiziModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_meizi, container, false);
        ButterKnife.bind(this, rootView);

        mMeiziModel = new MeiziModel(getContext());

        initView();

        initData();

        return rootView;
    }

    private void initView() {

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMeizi.setLayoutManager(mLayoutManager);
        mAdapter = new MeiziAdapter(getContext());
        rvMeizi.setAdapter(mAdapter);

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
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
        mMeiziModel.getMeizi(1)
                .subscribe(beautyBean -> {
                    if (!mIsLoadMore) {
                        mLocalData = beautyBean;
                    }
                    mAdapter.setData(mLocalData);
                    mAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);
    }

    private void loadMore(boolean isPullDown) {
        page += 1;
        new Handler().postDelayed(() -> mMeiziModel.getMeizi(page)
                .subscribe(beautyBean -> {
                    if (isPullDown) {
                        mLocalData.results.addAll(0, beautyBean.results);
                    } else {
                        mLocalData.results.addAll(beautyBean.results);
                    }
                    mAdapter.notifyDataSetChanged();
                    srl.setRefreshing(false);
                }, Throwable::printStackTrace), 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMeiziModel.clear();
    }
}
