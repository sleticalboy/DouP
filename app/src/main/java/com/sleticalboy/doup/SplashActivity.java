package com.sleticalboy.doup;

import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.module.main.StartActivity;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class SplashActivity extends BaseActivity implements ISplashView {

    @Override
    protected void initView() {
//        boolean isFirst = SPUtils.getBoolean(ConstantValue.KEY_FIRST_LAUNCH, true);
//        if (isFirst) {
//            SPUtils.putBoolean(ConstantValue.KEY_FIRST_LAUNCH, false);
//            // 展示欢迎页之后进入主页面
//            StartActivity.actionStart(this);
//        } else {
//            StartActivity.actionStart(this);
//        }
        StartActivity.actionStart(this);
        finish();
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingEnd() {

    }

    @Override
    public void onNetError() {

    }
}
