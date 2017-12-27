package com.sleticalboy.doup.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(bindLayout(), null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件 id
     */
    protected abstract int bindLayout();
}
