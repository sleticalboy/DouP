package com.sleticalboy.doup.fragment.meizi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.MeiziAdapter;
import com.sleticalboy.doup.bean.meizi.BeautyBean;
import com.sleticalboy.doup.http.ApiFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class MeiziFragment extends Fragment {

    private static final String TAG = "MeiziFragment";

    @BindView(R.id.rv_meizi)
    RecyclerView rvMeizi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private Unbinder unbinder;
    private MeiziAdapter mAdapter;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private GridLayoutManager mLayoutManager;

    private int page = 1;
    private int mLastVisibleItem;
    private boolean mIsLoadMore;
    private BeautyBean mLocalData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_meizi, null);
        unbinder = ButterKnife.bind(this, rootView);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMeizi.setLayoutManager(mLayoutManager);

        mAdapter = new MeiziAdapter(getContext());
        rvMeizi.setAdapter(mAdapter);

        Log.d(TAG, "init data");
        initData();

        return rootView;
    }

    private void initData() {
        ApiFactory.getBeautyApi().getBeauty(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beautyBean -> {
                    Log.d(TAG, "beautyBean:" + beautyBean);
                    Log.d(TAG, "Thread.currentThread():" + Thread.currentThread());
                    showMeizi(beautyBean);
                }, tr -> {
                    tr.printStackTrace();
                    Toast.makeText(getContext(), "加载数据失败", Toast.LENGTH_SHORT).show();
                });
    }

    private void showMeizi(BeautyBean beautyBean) {

        if (mIsLoadMore) {
            mLocalData.results.addAll(beautyBean.results);
            mAdapter.notifyDataSetChanged();
        } else {
            mLocalData = beautyBean;
            // 给 adapter 设置数据
            mAdapter.setData(mLocalData);
            mAdapter.notifyDataSetChanged();
        }

        scrollRecyclerView();

        setSwipeRefreshLayout();
    }

    private void setSwipeRefreshLayout() {
        srl.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        srl.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                getResources().getDisplayMetrics()));
        srl.setOnRefreshListener(() -> {
            mAdapter.notifyDataSetChanged();
            srl.setRefreshing(false);
        });
    }

    private void scrollRecyclerView() {
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1)
                        return;
                    if (mLayoutManager.getItemCount() == mLastVisibleItem + 1) {
                        mIsLoadMore = true;
                        new Handler().postDelayed(() -> {
                            // 加载更多数据
                            loadMoreData();
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        };
        rvMeizi.addOnScrollListener(mOnScrollListener);
    }

    private void loadMoreData() {
        page += 1;
        ApiFactory.getBeautyApi().getBeauty(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beautyBean -> {
                    Log.d(TAG, "beautyBean:" + beautyBean);
                    showMeizi(beautyBean);
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "加载数据失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = ["
                + savedInstanceState + "]");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (rvMeizi != null && mOnScrollListener != null) {
            rvMeizi.removeOnScrollListener(mOnScrollListener);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }
}
