package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.openeye.OpeneyeModel;
import com.sleticalboy.doup.module.openeye.adapter.FindingAdapter;

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
                    mAdapter.addAll(findingBeans);
                    mAdapter.notifyDataSetChanged();
                }, throwable -> {
                    throwable.printStackTrace();
                    mFindingView.onNetError();
                });

    }

    public void clickItem(int position) {
        String name = mAdapter.getItem(position).name;
        mFindingView.showFindingDetail(name);
    }

    public void initRecyclerView() {
        mFindingView.setLayoutManager();
        mFindingView.setAdapter(mAdapter = new FindingAdapter(getContext()));
    }

    @Override
    protected void setAdapter() {
    }

    @Override
    protected void setLayoutManager() {
    }
}
