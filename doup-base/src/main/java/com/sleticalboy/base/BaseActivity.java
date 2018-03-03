package com.sleticalboy.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.util.ActivityController;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    protected Unbinder unbinder;
    protected LifecycleCallback lifecycleCallback = LifecycleController.Companion.getInstance().getLifecycleCallback();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        ActivityController.INSTANCE.add(this);
        lifecycleCallback.onCreate(this, savedInstanceState);

        prepareTask();

        setContentView(attachLayout());
        unbinder = ButterKnife.bind(this);

        initView();
    }

    /**
     * 做一些准备工作，对于某些页面需要有特殊处理，都放在此处处理。比如隐藏状态栏等。
     */
    protected void prepareTask() {
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() called");
        lifecycleCallback.onActivityStart(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
//        JPushManager.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
//        JPushManager.getInstance().onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化 ToolBar
     *
     * @param toolbar         ToolBar
     * @param homeAsUpEnabled 是否显示 home 按钮
     * @param title           标题
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    /**
     * 绑定布局
     *
     * @return layout weatherId
     */
    protected abstract int attachLayout();

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
        unbinder.unbind();
        ActivityController.INSTANCE.remove(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 初始化 url 前缀
     *
     * @return url prefix: <code>doup://</code>
     */
    protected final String urlPre() {
//        return mUrlSchema + mUrlMiddle;
        return ConstantValue.Companion.getURL_PRE();
    }

    public final String getJumpUrl(String module, String activity) {
        return Uri.parse(urlPre() + module).toString() + activity;
    }

    public final String getTargetUrl(@StringRes int moduleNameResId, @StringRes int activityNameResId) {
        return getJumpUrl(getString(moduleNameResId), getString(activityNameResId));
    }
}
