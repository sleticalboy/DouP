package com.sleticalboy.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * <pre>
 *   Created by Android Studio.
 *   实现了懒加载机制的 Fragment, 用于 ViewPager + Fragment 中显示页面
 *   Date: 1/4/18.
 * </pre>
 *
 * @author sleticalboy
 */
public abstract class LazyFragment extends BaseFragment {

    /**
     * onCreateView 方法执行完毕
     */
    protected boolean isViewCreated;
    /**
     * Fragment 对用户是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 数据是否初始化
     */
    protected boolean isDataInitialized;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // step 1 当 View 创建完成之后 isViewCreated 置为 true
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * 实现懒加载的关键方法
     *
     * @param isVisibleToUser ui 对用户是否可见
     */
    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // step 2
            this.isVisibleToUser = true;
            lazyLoad();
        } else {
            this.isVisibleToUser = false;
        }
        Log.d(TAG, "setUserVisibleHint() called with: isVisibleToUser = [" + isVisibleToUser + "]");
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoad() {
        // step 3 通过双重判断才加载数据
        if (isVisibleToUser && isViewCreated && !isDataInitialized) {
            Log.d(TAG, "fetch data");
            fetchData();
            isDataInitialized = true;
            isViewCreated = false;
            isVisibleToUser = false;
        }
    }

    // step 4: 定义抽象方法让子类实现去获取数据

    /**
     * 获取数据
     */
    protected abstract void fetchData();
}
