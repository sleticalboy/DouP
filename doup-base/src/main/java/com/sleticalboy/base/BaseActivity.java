package com.sleticalboy.base;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sleticalboy.annotation.ButterKnife;
import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.util.OSUtils;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    protected final boolean DBG = Log.isLoggable(TAG, Log.DEBUG);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        handleStatusBar(R.color.status_bar_color, true, false);
        super.onCreate(savedInstanceState);
        beforeViews();
        setContentView(attachLayout());
        ButterKnife.bind(this);
        initView(savedInstanceState);
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
     *
     * @param savedInstanceState
     */
    protected abstract void initView(final Bundle savedInstanceState);

    /**
     * 4, View 初始化完成之后的逻辑
     */
    protected void afterViews() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
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
       // return mUrlSchema + mUrlMiddle;
        return ConstantValue.URL_PRE;
    }

    public void debug(String tag, String msg) {
        Log.d(tag, msg);
    }
}
