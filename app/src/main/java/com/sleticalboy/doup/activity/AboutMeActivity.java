package com.sleticalboy.doup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sleticalboy.doup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class AboutMeActivity extends AppCompatActivity {

    private static final String GIT_HUB = "https://www.github.com/sleticalboy";
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.about_me)
    TextView aboutMe;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);

        initHeader();

        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(GIT_HUB);
        webView.setWebViewClient(new WebViewClient());
    }

    private void initHeader() {
        aboutMe.setVisibility(View.GONE);
        tabTitle.setText(R.string.about_me);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
