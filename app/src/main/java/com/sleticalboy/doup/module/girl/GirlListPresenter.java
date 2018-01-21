package com.sleticalboy.doup.module.girl;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.GirlModel;
import com.sleticalboy.doup.model.girl.GirlBean;

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
    private GirlListAdapter mAdapter;
    private GirlFragment mGirlView;
    private int mPage = 1;

    public GirlListPresenter(Context context, GirlFragment girlView) {
        super(context);
        mGirlView = girlView;
        mGirlModel = new GirlModel(context);
    }

    public void initData() {
        mGirlView.onLoad();
        mGirlModel.getMeizi(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    mAdapter.addAll(girlBean.results);
                    mGirlView.onLoadFinished();
                }, Throwable::printStackTrace);
    }

    public void loadMore(boolean isPullDown) {
        mGirlModel.getMeizi(mPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlBean -> {
                    if (isPullDown) {
                        List<GirlBean.ResultsBean> allData = mAdapter.getAllData();
                        mAdapter.clear();
                        allData.addAll(0, girlBean.results);
                        mAdapter.addAll(allData);
                        mAdapter.notifyDataSetChanged();
                        mGirlView.onLoadFinished();
                    } else {
                        mAdapter.addAll(girlBean.results);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mGirlModel.clear();
    }

    public void initRecyclerView() {
        mGirlView.setLayoutManager();
        mAdapter = new GirlListAdapter(getContext());
        mGirlView.setAdapter(mAdapter);
    }

    @Override
    public void setAdapter() {
    }

    @Override
    protected void setLayoutManager() {
    }
}
