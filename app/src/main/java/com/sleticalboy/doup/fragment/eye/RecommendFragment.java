package com.sleticalboy.doup.fragment.eye;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.eye.RecommendAdapter;
import com.sleticalboy.doup.bean.eye.RecommendBean;
import com.sleticalboy.doup.http.ApiFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class RecommendFragment extends Fragment {

    private static final String TAG = "RecommendFragment";

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private RecommendAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItemIndex;
    private List<RecommendBean.IssueListBean.ItemListBean> mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_recommond, null);
        ButterKnife.bind(this, rootView);

        Log.d(TAG, "initView");
        initView();

        Log.d(TAG, "initData");
        initData();

        return rootView;
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(getContext());
        rvRecommend.setLayoutManager(mLayoutManager);

        Log.d(TAG, "init adapter");
        mAdapter = new RecommendAdapter(getContext());
        rvRecommend.setAdapter(mAdapter);

        rvRecommend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLayoutManager.getItemCount() == 1) {
                        return;
                    }
                    if (mLayoutManager.getItemCount() + 1 == mLastVisibleItemIndex) {
                        //
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
            } else {
                srl.setRefreshing(true);
            }
        });
    }

    private void initData() {

        ApiFactory.getEyesApi().getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendBean -> {
                    // 最终需要的是 type 是 video 的 ItemListBean 所以需要对原始数据进行处理和过滤
                    flatMapData(recommendBean);
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                });

    }

    private void flatMapData(RecommendBean recommendBean) {
        Observable.from(recommendBean.issueList)
                .flatMap(issueListBean -> Observable.from(issueListBean.itemList))
                .filter(itemListBean -> itemListBean.type.equals("video"))
                .forEach(itemListBean -> mData.add(itemListBean));

        Log.d(TAG, "mData.size():" + mData.size());
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
    }
}
