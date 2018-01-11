package com.sleticalboy.doup.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public abstract class BaseFragment extends Fragment {

    public final String TAG = getClass().getSimpleName();

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(attachLayout(), null);
        mUnbinder = ButterKnife.bind(this, rootView);

        initView(rootView);

        return rootView;
    }

    /**
     * 初始化 ToolBar
     *
     * @param toolbar         ToolBar
     * @param homeAsUpEnabled 是否显示 home 按钮
     * @param title           标题
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }

    /**
     * 初始化控件
     *
     * @param rootView 根视图
     */
    protected abstract void initView(View rootView);

    /**
     * 绑定布局文件
     *
     * @return 布局文件 weatherId
     */
    protected abstract int attachLayout();

    /**
     * 初始化 url 前缀
     *
     * @return url prefix: <code>doup://</code>
     */
    public final String urlPre() {
        return ((BaseActivity) getActivity()).urlPre();
    }

    public final String getTargetUrl(@StringRes int moduleResId, @StringRes int activityResId) {
        return ((BaseActivity) getActivity()).getTargetUrl(moduleResId, activityResId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
