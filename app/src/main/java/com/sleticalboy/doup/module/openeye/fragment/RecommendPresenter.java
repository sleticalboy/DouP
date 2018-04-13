package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;
import android.util.Log;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.bean.openeye.ItemListBean;
import com.sleticalboy.doup.bean.openeye.RecommendBean;
import com.sleticalboy.doup.bean.openeye.VideoBean;
import com.sleticalboy.doup.model.openeye.OpeneyeModel;
import com.sleticalboy.doup.module.openeye.adapter.RecommendAdapter;

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

    private IRecommendView mRecommendView;
    private OpeneyeModel mOpeneyeModel;
    private RecommendAdapter mAdapter;
    private String mDate;

    public RecommendPresenter(Context context, IRecommendView recommendView) {
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
                    flatMapDataAndNotifyAdapter(recommendBean, false);
                }, throwable -> mRecommendView.onNetError());
    }

    private void flatMapDataAndNotifyAdapter(RecommendBean recommendBean, boolean isPullDown) {
        List<ItemListBean> allData = mAdapter.getAllData();
        if (isPullDown) {
            mAdapter.clear();
        }
        Observable.fromIterable(recommendBean.issueList)
                .flatMap(issueListBean -> Observable.fromIterable(issueListBean.itemList))
                .filter(itemListBean -> {
                    // 过滤出所有的 video
                    return itemListBean.type.equals("video");
                })
                .forEach(itemListBean -> {
                    if (isPullDown) {
                        Log.d(TAG, "下拉加载");
                        allData.add(0, itemListBean);
                    } else {
                        Log.d(TAG, "上拉加载");
                        allData.add(itemListBean);
                    }
                });
        mAdapter.addAll(allData);
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
        mOpeneyeModel.getMoreRecommend(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendBean -> {
                    resolveDate(recommendBean);
                    flatMapDataAndNotifyAdapter(recommendBean, isPullDown);
                }, throwable -> mRecommendView.onNetError());
    }

    public void clickItem(int position) {
        VideoBean videoBean = wrapperVideo(mAdapter.getItem(position));
        mRecommendView.showVideoDetail(videoBean);
    }

    private VideoBean wrapperVideo(ItemListBean itemData) {
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

    public void initRecyclerView() {
        mRecommendView.setLayoutManager();
        mRecommendView.setAdapter(mAdapter = new RecommendAdapter(getContext()));
    }

    @Override
    protected void setAdapter() {
    }

    @Override
    protected void setLayoutManager() {
    }
}
