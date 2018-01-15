package com.sleticalboy.doup.module.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.base.IBaseView;
import com.sleticalboy.doup.model.news.NewsDetailBean;
import com.sleticalboy.util.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class NewsDetailActivity extends BaseActivity implements IBaseView {

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
    private NewsDetailPresenter mPresenter;

    @Override
    protected void initView() {
        mPresenter = new NewsDetailPresenter(this, this);
        mPresenter.getNewsData(String.valueOf(id));
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void prepareTask() {
        Intent intent = getIntent();
        if (intent != null)
            id = intent.getIntExtra(ID, -1);
    }

    public void showToolBar(String title) {
        super.initToolBar(toolBar, true, title);
        collapsingToolbar.setTitle(title);
    }

    public void showHeader(NewsDetailBean newsDetailBean) {
        ImageLoader.load(this, newsImg, newsDetailBean.image);
        newsTitle.setText(newsDetailBean.title);
        newsImgResource.setText(newsDetailBean.image_source);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showWebView(String html) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    public static void actionStart(Context context, int id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    @OnClick(R.id.fab_share)
    public void onViewClicked() {
        mPresenter.shareNews();
    }

    public void showShareView(String url) {
        // FIXME: 1/15/18 分享功能，暂时调用系统分享
        Log.d(TAG, url);
        ArrayList<String> data = new ArrayList<>();
        data.add(url);

        Intent target = new Intent();
        target.setAction(Intent.ACTION_SEND_MULTIPLE);
        target.putStringArrayListExtra(Intent.EXTRA_TEXT, data);
        target.setType("*/*");
        startActivity(Intent.createChooser(target, "分享到"));
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
