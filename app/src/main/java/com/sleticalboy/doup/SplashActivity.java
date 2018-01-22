package com.sleticalboy.doup;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.config.ConstantValue;
import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.SPUtils;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    @Override
    protected void initView() {
        mPresenter = createPresenter();
        boolean isFirst = SPUtils.getBoolean(ConstantValue.KEY_FIRST_LAUNCH, true);
        if (isFirst) {
            SPUtils.putBoolean(ConstantValue.KEY_FIRST_LAUNCH, false);
            // 展示欢迎页之后进入主页面
            mPresenter.toMain();
        } else {
            mPresenter.toMain();
        }
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_splash;
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
        return new SplashPresenter(this, this);
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
