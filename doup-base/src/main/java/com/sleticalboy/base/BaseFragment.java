package com.sleticalboy.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.sleticalboy.annotation.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();
    protected final boolean DBG = Log.isLoggable(TAG, Log.DEBUG);

    @Override
    public void onAttach(Context context) {
        debug("onAttach() called with: context = [" + context + "]");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        debug("onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        debug("onCreateView() called with: inflater = [" + inflater + "], container = [" + container
                + "], savedInstanceState = [" + savedInstanceState + "]");
        prepareTask();

        View rootView = inflater.inflate(attachLayout(), null);
        ButterKnife.bind(this, rootView);

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
        debug("onViewCreated() called with: view = [" + view + "], savedInstanceState = ["
                + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        debug("onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        debug("onStart() called");
        super.onStart();
    }

    @Override
    public void onResume() {
        debug("onResume() called");
        super.onResume();
    }

    @Override
    public void onPause() {
        debug("onPause() called");
        super.onPause();
    }

    @Override
    public void onStop() {
        debug("onStop() called");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        debug("onDestroyView() called");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        debug("onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        debug("onDetach() called");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        debug("onSaveInstanceState() called with: outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        debug("onViewStateRestored() savedInstanceState = [" + savedInstanceState + "]");
        super.onViewStateRestored(savedInstanceState);
    }

    private void debug(String msg) {
        if (DBG) {
            Log.d("BaseFragment", msg);
        }
    }
}
