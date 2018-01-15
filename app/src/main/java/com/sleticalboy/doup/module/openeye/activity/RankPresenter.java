package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.HotBean;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.adapter.RankAdapter;

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

    private RankAdapter mAdapter;
    private FindingDetailActivity mRankView;
    private LinearLayoutManager mLayoutManager;
    private List<HotBean.ItemListBean.DataBean> mData = new ArrayList<>();
    private OpeneyeModel mOpeneyeModel;
    private String mDate;

    public RankPresenter(Context context, FindingDetailActivity rankView) {
        super(context);
        mRankView = rankView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    public void initData(String name) {
        mOpeneyeModel.getFindingsDetail(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::resolveDate)
                .subscribe(this::flatMapData);
    }

    private void resolveDate(HotBean hotBean) {
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(hotBean.nextPageUrl);
        mDate = matcher.replaceAll("")
                .subSequence(1, matcher.replaceAll("").length() - 1)
                .toString();
    }

    private void flatMapData(HotBean hotBean) {
        Observable.fromIterable(hotBean.itemList)
                .filter(itemListBean -> itemListBean.data.category != null
                        && itemListBean.data.cover != null)
                .forEach(itemListBean -> {
                    Log.d(TAG, itemListBean.data.toString());
                    mData.add(itemListBean.data);
                });
        mAdapter.addAll(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRankView.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    @Override
    protected void setAdapter() {
        mAdapter = new RankAdapter(getContext(), mData);
        mRankView.getRecyclerView().setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mRankView);
    }

    public int findLastVisibleItemPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    public int getItemCount() {
        return mLayoutManager.getItemCount();
    }

    public void loadMore(boolean isPullDown) {
        // TODO: 1/15/18 加载更多
    }

    public void onItemClick(int position) {
        VideoBean videoBean = wrapperVideo(mData.get(position));
        mRankView.showVideoDetail(videoBean);
    }

    private VideoBean wrapperVideo(HotBean.ItemListBean.DataBean data) {
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
