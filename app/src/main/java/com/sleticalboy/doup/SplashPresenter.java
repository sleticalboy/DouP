package com.sleticalboy.doup;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class SplashPresenter extends BasePresenter implements
        SplashContract.Presenter {

    private SplashContract.View mSplashView;

    public SplashPresenter(Context context, SplashContract.View splashView) {
        super(context);
        mSplashView = splashView;
    }

    @Override
    public void toMain() {
        mSplashView.showMain();
    }
}
