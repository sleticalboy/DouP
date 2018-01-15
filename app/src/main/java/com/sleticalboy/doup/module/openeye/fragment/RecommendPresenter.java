package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.RecommendBean;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.adapter.RecommendAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class RecommendPresenter extends BasePresenter {

    public static final String TAG = "RecommendPresenter";

    private RecommendFragment mRecommendView;
    private OpeneyeModel mOpeneyeModel;
    private RecommendAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<RecommendBean.IssueListBean.ItemListBean> mData = new ArrayList<>();
    private String mDate;
    private boolean mIsPullDown = true;

    public RecommendPresenter(Context context, RecommendFragment recommendView) {
        super(context);
        mRecommendView = recommendView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    public void initData() {
        mOpeneyeModel.getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendBean -> {
                    // 最终需要的是 type 是 video 的 ItemListBean 所以需要对原始数据进行处理和过滤
                    resolveDate(recommendBean);
                    flatMapData(recommendBean);
                }, throwable -> mRecommendView.onNetError());
    }

    private void flatMapData(RecommendBean recommendBean) {
        Observable.fromIterable(recommendBean.issueList)
                .flatMap(issueListBean -> Observable.fromIterable(issueListBean.itemList))
                .filter(itemListBean -> itemListBean.type.equals("video"))
                .forEach(itemListBean -> {
                    if (mIsPullDown) {
                        mData.add(0, itemListBean);
                    } else {
                        mData.add(itemListBean);
                    }
                });

        Log.d(TAG, "mData.size():" + mData.size());
        mAdapter.addAll(mData);
        mAdapter.notifyDataSetChanged();
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

    public void loadMore(boolean isPullDown) {
        mIsPullDown = isPullDown;
        mOpeneyeModel.getMoreRecommend(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendBean -> {
                    resolveDate(recommendBean);
                    flatMapData(recommendBean);
                }, throwable -> mRecommendView.onNetError());
    }

    @Override
    protected void setAdapter() {
        mAdapter = new RecommendAdapter(getContext(), mData);
        mRecommendView.getRecyclerView().setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mRecommendView);
    }

    @Override
    protected void setLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecommendView.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    public int findLastVisibleItemPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    public int getItemCount() {
        return mLayoutManager.getItemCount();
    }

    public void onItemClick(int position) {
        VideoBean videoBean = wrapperVideo(mData.get(position));
        mRecommendView.showVideoDetail(videoBean);
    }

    private VideoBean wrapperVideo(RecommendBean.IssueListBean.ItemListBean itemData) {
        if (itemData != null) {
            VideoBean videoBean = new VideoBean();
            videoBean.title = itemData.data.title;
            videoBean.playUrl = itemData.data.playUrl;
            videoBean.category = itemData.data.category;
            videoBean.desc = itemData.data.description;
            videoBean.feed = itemData.data.cover.feed;
            videoBean.time = System.currentTimeMillis();
            videoBean.duration = itemData.data.duration;
            videoBean.blurred = itemData.data.cover.blurred;
            videoBean.shareCount = itemData.data.consumption.shareCount;
            videoBean.replyCount = itemData.data.consumption.replyCount;
            videoBean.collectCount = itemData.data.consumption.collectionCount;
            return videoBean;
        }
        return null;
    }
}
