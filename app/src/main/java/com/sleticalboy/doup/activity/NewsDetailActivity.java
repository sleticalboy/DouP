package com.sleticalboy.doup.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.news.NewsDetailBean;
import com.sleticalboy.doup.http.ApiFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";
    private static final String ID = "id";

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.news_img)
    ImageView newsImg;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_img_resource)
    TextView newsImgResource;
    @BindView(R.id.web_view)
    WebView webView;

    private int id;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mUnbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null)
            id = intent.getIntExtra(ID, -1);
        getNewsDetail(String.valueOf(id));
    }

    private void getNewsDetail(String id) {
        ApiFactory.getNewsApi().getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetailBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(NewsDetailActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(NewsDetailActivity.this, "服务器繁忙", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(NewsDetailBean newsDetailBean) {
                        Log.d(TAG, newsDetailBean.title);
                        initToolBar(newsDetailBean.title);
                        showPage(newsDetailBean);
                    }
                });
    }

    // app bar
    private void initToolBar(String title) {
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(title);
    }

    // 显示页面
    private void showPage(NewsDetailBean newsDetailBean) {
        // 显示头部
        initHeader(newsDetailBean);
        // 加载 WebView
        initWebView(newsDetailBean);
    }

    private void initHeader(NewsDetailBean newsDetailBean) {
        Glide.with(this).load(newsDetailBean.image).centerCrop().into(newsImg);
        newsTitle.setText(newsDetailBean.title);
        newsImgResource.setText(newsDetailBean.image_source);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(NewsDetailBean newsDetailBean) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + newsDetailBean.css.get(0) + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + newsDetailBean.body.replace(img, " ");
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    public static void actionStart(Context context, int id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.fab_share)
    public void onViewClicked() {
        Toast.makeText(this, "分享本篇", Toast.LENGTH_SHORT).show();
    }
}
