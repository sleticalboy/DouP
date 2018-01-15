package com.sleticalboy.doup.module.girl;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.GirlModel;
import com.sleticalboy.doup.model.girl.GirlBean;

import java.util.ArrayList;
import java.util.List;

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
public class GirlListPresenter extends BasePresenter {

    private GirlModel mGirlModel;
    private GridLayoutManager mLayoutManager;
    private GirlListAdapter mAdapter;
    private GirlFragment mGirlView;
    private List<GirlBean.ResultsBean> mData = new ArrayList<>();
    private int mPage = 1;

    public GirlListPresenter(Context context, GirlFragment girlView) {
        super(context);
        mGirlView = girlView;
        mGirlModel = new GirlModel(context);
        initData();
    }

    @Override
    public void setAdapter() {
        mAdapter = new GirlListAdapter(getContext(), mData);
        mGirlView.getRecyclerView().setAdapter(mAdapter);
    }

    @Override
    protected void setLayoutManager() {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mGirlView.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    private void initData() {
        mGirlModel.getMeizi(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    mData.addAll(girlBean.results);
                    mAdapter.addAll(mData);
                    mAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);
    }


    public void loadMore(boolean isPullDown) {
        mGirlModel.getMeizi(mPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    if (isPullDown) {
                        mData.addAll(girlBean.results);
                        mGirlView.onLoadingOver();
                    } else {
                        mData.addAll(0, girlBean.results);
                    }
                    mAdapter.addAll(mData);
                    mAdapter.notifyDataSetChanged();
                });
    }

    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mGirlModel.clear();
    }

    public int findLastVisibleItemPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    public int getItemCount() {
        return mLayoutManager.getItemCount();
    }
}
