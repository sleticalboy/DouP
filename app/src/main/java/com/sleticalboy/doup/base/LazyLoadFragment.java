package com.sleticalboy.doup.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public abstract class LazyLoadFragment<T extends IBasePresenter> extends BaseFragment<T> {

    protected boolean isViewInitialized;
    protected boolean isDataInitialized;
    protected boolean isVisibleToUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitialized = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareLoadData();
    }

    private boolean prepareLoadData() {
        return prepareLoadData(false);
    }

    private boolean prepareLoadData(boolean forceToUpdate) {
        if (isViewInitialized && isVisibleToUser && (!isDataInitialized || forceToUpdate)) {
            fetchData();
            isDataInitialized = true;
            return true;
        }
        return false;
    }

    /**
     * 获取数据
     */
    protected abstract void fetchData();
}
