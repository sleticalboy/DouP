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
import com.sleticalboy.doup.mvp.model.bean.eye.RecommendBean;
import com.sleticalboy.doup.mvp.model.EyesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

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
    private String mDate;
    private boolean mIsPullDown = true;

    private EyesModel mEyesModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_recommond, null);
        ButterKnife.bind(this, rootView);

        mEyesModel = new EyesModel(getContext());

        initView();

        initData();

        return rootView;
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(getContext());
        rvRecommend.setLayoutManager(mLayoutManager);

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
                        // 上拉加载更多数据
                        mIsPullDown = false;
                        loadMore();
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
                mIsPullDown = true;
                loadMore();
            } else {
                srl.setRefreshing(true);
            }
        });
    }

    private void loadMore() {
        mEyesModel.getMoreRecommend(mDate)
                .subscribe(recommendBean -> {
                    resolveDate(recommendBean);
                    flatMapData(recommendBean);
                }, this::loadMoreError);
    }

    private void loadMoreError(Throwable tr) {
        tr.printStackTrace();
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        mEyesModel.getRecommend()
                .subscribe(recommendBean -> {
                    // 最终需要的是 type 是 video 的 ItemListBean 所以需要对原始数据进行处理和过滤
                    resolveDate(recommendBean);
                    flatMapData(recommendBean);
                }, this::loadMoreError);

    }

    private void resolveDate(RecommendBean recommendBean) {
        String nextPageUrl = recommendBean.nextPageUrl;
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nextPageUrl);
        mDate = matcher.replaceAll("")
                .subSequence(1, matcher.replaceAll("").length() - 1)
                .toString();
    }

    private void flatMapData(RecommendBean recommendBean) {
        Observable.from(recommendBean.issueList)
                .flatMap(issueListBean -> Observable.from(issueListBean.itemList))
                .filter(itemListBean -> itemListBean.type.equals("video"))
                .forEach(itemListBean -> {
                    if (mIsPullDown)
                        mData.add(0, itemListBean);
                    else
                        mData.add(itemListBean);
                });

        Log.d(TAG, "mData.size():" + mData.size());
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
    }
}
