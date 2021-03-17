package com.sleticalboy.doup;

import android.os.Bundle;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.Prefs;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    @Override
    protected void initView(final Bundle savedInstanceState) {
        if (Prefs.getBoolean(ConstantValue.KEY_FIRST_LAUNCH, true)) {
            Prefs.putBoolean(ConstantValue.KEY_FIRST_LAUNCH, false);
            // 展示欢迎页之后进入主页面
            createPresenter().toMain();
        } else {
            createPresenter().toMain();
        }
    }

    @Override
    protected int attachLayout() {
        return R.layout.main_activity_splash;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
    }

    @Override
    public void onNetError() {
    }

    @Override
    public SplashContract.Presenter createPresenter() {
        if (mPresenter == null) mPresenter = new SplashPresenter(this, this);
        return mPresenter;
    }

    @Override
    public void showSplash() {
    }

    @Override
    public void showMain() {
        StartActivity.actionStart(this);
        this.finish();
    }
}
