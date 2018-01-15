package com.sleticalboy.doup.module.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.base.IBaseView;

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
    protected void initView() {
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

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingOver() {

    }

    @Override
    public void onNetError() {

    }
}
