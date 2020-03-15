package com.sleticalboy.doup.module.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sleticalboy.annotation.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 1/8/18.
 *
 * @author sleticalboy
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        beforeViews(window);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        return inflateRootView(inflater, window.findViewById(android.R.id.content), savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setupViews();
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
    }
}
