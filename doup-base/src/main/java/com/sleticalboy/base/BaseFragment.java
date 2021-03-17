package com.sleticalboy.base;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(attachLayout(), null);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareTask();
    }
}
