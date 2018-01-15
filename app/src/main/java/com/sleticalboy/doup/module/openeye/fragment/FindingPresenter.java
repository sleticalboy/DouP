package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.FindingBean;
import com.sleticalboy.doup.module.openeye.adapter.FindingAdapter;

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
public class FindingPresenter extends BasePresenter {

    private FindingFragment mFindingView;
    private OpeneyeModel mOpeneyeModel;
    private FindingAdapter mAdapter;
    private List<FindingBean> mData = new ArrayList<>();

    public FindingPresenter(Context context, FindingFragment findingView) {
        super(context);
        mFindingView = findingView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    public void initData() {
        mOpeneyeModel.getFindings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findingBeans -> {
                    mData = findingBeans;
                    mAdapter.addAll(mData);
                    mAdapter.notifyDataSetChanged();
                }, throwable -> {
                    throwable.printStackTrace();
                    mFindingView.onNetError();
                });

    }

    @Override
    protected void setAdapter() {
        mAdapter = new FindingAdapter(getContext(), mData);
        mFindingView.getRecyclerView().setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mFindingView);
    }

    @Override
    protected void setLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mFindingView.getRecyclerView().setLayoutManager(layoutManager);
    }

    public void onItemClick(int position) {
        String name = mData.get(position).name;
        mFindingView.showFindingDetail(name);
    }
}
