package com.sleticalboy.doup.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.jpush.JPushManager;
import com.sleticalboy.util.ActivityController;

import butterknife.BindString;
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

    @BindString(R.string.url_schema)
    String mUrlSchema;
    @BindString(R.string.url_middle)
    String mUrlMiddle;

    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.add(this);

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
    protected void onResume() {
        super.onResume();
        JPushManager.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushManager.getInstance().onPause(this);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityController.remove(this);
    }

    /**
     * 初始化 url 前缀
     *
     * @return url prefix: <code>doup://</code>
     */
    protected final String urlPre() {
//        return getResources().getString(R.string.url_schema) + getResources().getString(R.string.url_middle);
        return mUrlSchema + mUrlMiddle;
    }

    public final String getJumpUrl(String module, String activity) {
        return Uri.parse(urlPre() + module).toString() + activity;
    }

    public final String getTargetUrl(@StringRes int moduleNameResId, @StringRes int activityNameResId) {
        return getJumpUrl(getString(moduleNameResId), getString(activityNameResId));
    }
}
