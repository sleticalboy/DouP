package com.sleticalboy.doup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.doup.module.main.StartActivity;
import com.sleticalboy.util.SPUtils;

/**
 * Created by Android Studio.
 * Date: 12/30/17.
 *
 * @author sleticalboy
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isFirst = SPUtils.getBoolean(ConstantValue.KEY_FIRST_LAUNCH, true);
        if (isFirst) {
            SPUtils.putBoolean(ConstantValue.KEY_FIRST_LAUNCH, false);
            // 展示欢迎页之后进入主页面
            StartActivity.actionStart(this);
        } else {
            StartActivity.actionStart(this);
        }
    }
}
