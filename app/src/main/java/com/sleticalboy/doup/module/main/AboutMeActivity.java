package com.sleticalboy.doup.module.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.R;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public class AboutMeActivity extends BaseActivity implements IBaseView {

    private static final String GIT_HUB = "https://www.github.com/sleticalboy";

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void initView(final Bundle savedInstanceState) {
        initWebView();
    }

    @Override
    protected int attachLayout() {
        return R.layout.main_activity_about_me;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(GIT_HUB);
        webView.setWebViewClient(new WebViewClient());
    }

    public static void startAction(Context context) {
        context.startActivity(new Intent(context, AboutMeActivity.class));
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
}
