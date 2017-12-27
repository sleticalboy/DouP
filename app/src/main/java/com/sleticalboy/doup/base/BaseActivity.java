package com.sleticalboy.doup.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindContentView());
        unbinder = ButterKnife.bind(this);
        ActivityController.add(this);
    }

    /**
     * 绑定布局
     *
     * @return layout id
     */
    protected abstract int bindContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityController.remove(this);
    }
}
