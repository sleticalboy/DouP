package com.sleticalboy.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.util.ActivityController;
import com.sleticalboy.util.OSUtils;

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
    protected LifecycleCallback lifecycleCallback =
            LifecycleController.Companion.getInstance().getLifecycleCallback();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        handleStatusBar(R.color.status_bar_color, true, false);
        super.onCreate(savedInstanceState);
        ActivityController.INSTANCE.add(this);
        lifecycleCallback.onCreate(this, savedInstanceState);
        beforeViews();
        setContentView(attachLayout());
        unbinder = ButterKnife.bind(this);
        initView();
        afterViews();
    }

    /**
     * 1, View 初始化之前的逻辑。比如隐藏状态栏等。
     */
    protected void beforeViews() {
    }

    /**
     * 2, 绑定布局文件
     *
     * @return layout weatherId
     */
    protected abstract int attachLayout();

    /**
     * 3, View 初始化时的逻辑
     */
    protected abstract void initView();

    /**
     * 4, View 初始化完成之后的逻辑
     */
    protected void afterViews() {
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() called");
        super.onStart();
        lifecycleCallback.onActivityStart(this);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
        lifecycleCallback.onActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
        lifecycleCallback.onActivityDestroy(this);
        unbinder.unbind();
        ActivityController.INSTANCE.remove(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
        lifecycleCallback.onActivitySaveInstanceState(this, outState);
    }

    protected void handleStatusBar(@ColorRes int statusBarColor, boolean isNoTitle, boolean isFullScreen) {
        OSUtils.handleStatusBar(this, statusBarColor, isNoTitle, isFullScreen);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onRestoreInstanceState(savedInstanceState);
        lifecycleCallback.onActivityRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
        lifecycleCallback.onActivityPause(this);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
        lifecycleCallback.onActivityResume(this);
    }

    public final String getTargetUrl(@StringRes int moduleNameResId, @StringRes int activityNameResId) {
        return getJumpUrl(getString(moduleNameResId), getString(activityNameResId));
    }

    public final String getJumpUrl(String module, String activity) {
        return Uri.parse(urlPre() + module).toString() + activity;
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
}
