package com.sleticalboy.doup.module.todo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sleticalboy.doup.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 1/8/18.
 *
 * @author sleticalboy
 */
public abstract class BaseDialogFragment extends DialogFragment {

    Unbinder unbinder;

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        beforeViews(window);
        View rootView = inflateRootView(inflater, window.findViewById(android.R.id.content), savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setupViews();
        return rootView;
    }

    /**
     * 初始化 View 之前的逻辑
     *
     * @param window
     */
    protected void beforeViews(Window window) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @NonNull
    private View inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(attachLayout(), container, false);
    }

    /**
     * 设置控件，在 View 初始化完成后执行
     */
    protected abstract void setupViews();

    /**
     * 绑定自定义 dialog 布局文件
     *
     * @return 资源文件 id
     */
    protected abstract int attachLayout();

    /**
     * 点击确定按钮时的逻辑
     */
    protected abstract void postData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
