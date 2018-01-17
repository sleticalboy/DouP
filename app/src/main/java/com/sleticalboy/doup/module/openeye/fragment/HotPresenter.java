package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.DataBean;
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
    private List<DataBean> mData = new ArrayList<>();

    public HotPresenter(Context context, HotFragment hotView) {
        super(context);
        mHotView = hotView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    @Override
    protected void setAdapter() {
    }

    @Override
    protected void setLayoutManager() {
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

    public void loadMore(boolean isPullDown) {
        //
    }
}
