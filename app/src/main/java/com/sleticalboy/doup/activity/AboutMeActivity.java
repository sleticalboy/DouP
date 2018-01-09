package com.sleticalboy.doup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public class AboutMeActivity extends BaseActivity {

    private static final String GIT_HUB = "https://www.github.com/sleticalboy";

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void initData() {
        // do nothing
    }

    @Override
    protected void initView() {
        initHeader();
        initWebView();
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_about_me;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(GIT_HUB);
        webView.setWebViewClient(new WebViewClient());
    }

    private void initHeader() {
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }
}
