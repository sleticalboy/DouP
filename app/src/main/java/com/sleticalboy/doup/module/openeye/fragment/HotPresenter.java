package com.sleticalboy.doup.module.openeye.fragment;

import android.content.Context;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.HotBean;

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

    private HotFragment mPopularView;
    private OpeneyeModel mOpeneyeModel;

    public HotPresenter(Context context, HotFragment popularView) {
        super(context);
        mPopularView = popularView;
        mOpeneyeModel = new OpeneyeModel(getContext());
    }

    public void initData() {
        mOpeneyeModel.getPopular(0, "")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {

                    }
                });
    }
}
