package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.util.Log;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.bean.openeye.DataBean;
import com.sleticalboy.doup.bean.openeye.ItemListBean;
import com.sleticalboy.doup.bean.openeye.VideoBean;
import com.sleticalboy.doup.model.openeye.OpeneyeModel;
import com.sleticalboy.doup.module.openeye.adapter.RankAdapterBase;
import com.sleticalboy.doup.module.openeye.fragment.IRecommendView;
import com.sleticalboy.util.StrUtils;

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
public class RankPresenter extends BasePresenter {
    
    public static final String TAG = "RankPresenter";
    private static final Pattern DATE_PATTERN = Pattern.compile("[^0-9]");
    
    private RankAdapterBase mAdapter;
    private IRecommendView mRecommendView;
    private OpeneyeModel mOpeneyeModel;
    private String mDate;
    
    public RankPresenter(Context context, IRecommendView rankView) {
        super(context);
        mRecommendView = rankView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }
    
    @Override
    public void initRecyclerView() {
        mRecommendView.setLayoutManager();
        mRecommendView.setAdapter(mAdapter = new RankAdapterBase(getContext()));
    }
    
    public void initData(String name) {
        mOpeneyeModel.getFindingsDetail(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotBean -> {
                    resolveDate(hotBean.nextPageUrl);
                    flatMapDataAndNotifyAdapter(hotBean.itemList, false);
                });
    }
    
    private void resolveDate(String text) {
        if (text == null) {
            return;
        }
        Matcher matcher = DATE_PATTERN.matcher(text);
        mDate = matcher.replaceAll("")
                .subSequence(1, matcher.replaceAll("").length() - 1)
                .toString();
    }
    
    private void flatMapDataAndNotifyAdapter(List<ItemListBean> itemListBeans, boolean isPullDown) {
        if (itemListBeans == null) {
            return;
        }
        List<DataBean> allData = mAdapter.getAllData();
        if (isPullDown) {
            mAdapter.clear();
        }
        Observable.fromIterable(itemListBeans)
                .filter(itemListBean -> {
                    // 过滤出需要的数据
                    return itemListBean.data.category != null
                            && itemListBean.data.cover != null;
                })
                .forEach(itemListBean -> {
                    if (isPullDown) {
                        allData.add(0, itemListBean.data);
                    } else {
                        allData.add(itemListBean.data);
                    }
                });
        Log.d(TAG, "allData.size():" + allData.size());
        mAdapter.addAll(allData);
    }
    
    public void loadMore(boolean isPullDown) {
        mOpeneyeModel.getMoreRecommend(mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(recommendBean -> !StrUtils.isEmpty(recommendBean.nextPageUrl))
                .subscribe(recommendBean -> {
                    resolveDate(recommendBean.nextPageUrl);
                    final List<ItemListBean> list = new ArrayList<>();
                    Observable.fromIterable(recommendBean.issueList)
                            .forEach(issueListBean -> list.addAll(issueListBean.itemList));
                    flatMapDataAndNotifyAdapter(list, isPullDown);
                });
    }
    
    public void clickItem(int position) {
        VideoBean videoBean = wrapperVideo(mAdapter.getItem(position));
        mRecommendView.showVideoDetail(videoBean);
    }
    
    private VideoBean wrapperVideo(DataBean data) {
        if (data != null) {
            VideoBean videoBean = new VideoBean();
            videoBean.title = data.title;
            videoBean.playUrl = data.playUrl;
            videoBean.category = data.category;
            videoBean.desc = data.description;
            videoBean.feed = data.cover.feed;
            videoBean.time = System.currentTimeMillis();
            videoBean.duration = data.duration;
            videoBean.blurred = data.cover.blurred;
            videoBean.shareCount = data.consumption.shareCount;
            videoBean.replyCount = data.consumption.replyCount;
            videoBean.collectCount = data.consumption.collectionCount;
            return videoBean;
        }
        return null;
    }
}
