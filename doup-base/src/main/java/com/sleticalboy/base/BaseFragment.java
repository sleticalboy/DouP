package com.sleticalboy.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container
                + "], savedInstanceState = [" + savedInstanceState + "]");
        prepareTask();

        View rootView = inflater.inflate(attachLayout(), null);
        mUnbinder = ButterKnife.bind(this, rootView);

        initView(rootView);

        return rootView;
    }

    protected void prepareTask() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = ["
                + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart() called");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called");
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach() called");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewStateRestored() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onViewStateRestored(savedInstanceState);
    }
}
