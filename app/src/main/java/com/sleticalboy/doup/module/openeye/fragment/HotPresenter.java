package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.HotBean;
import com.sleticalboy.doup.module.openeye.adapter.RankAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
public class HotPresenter extends BasePresenter {

    private HotFragment mHotView;
    private OpeneyeModel mOpeneyeModel;
    private RankAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<HotBean.ItemListBean.DataBean> mData = new ArrayList<>();

    public HotPresenter(Context context, HotFragment hotView) {
        super(context);
        mHotView = hotView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    @Override
    protected void setAdapter() {
        mAdapter = new RankAdapter(getContext(), mData);
        mHotView.getRecyclerView().setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mHotView);
    }

    @Override
    protected void setLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mHotView.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    public void initData() {
        // FIXME: 1/16/18 获取数据
        mOpeneyeModel.getPopular(10, "date")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {

                    }
                });
    }
}
