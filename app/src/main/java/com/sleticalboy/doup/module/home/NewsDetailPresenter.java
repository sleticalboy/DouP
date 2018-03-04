package com.sleticalboy.doup.module.home;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;
import com.sleticalboy.doup.bean.news.NewsDetailBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/11/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class NewsDetailPresenter extends BasePresenter {

    public static final String TAG = "NewsDetailPresenter";

    private NewsModel mNewsModel;
    private NewsDetailActivity mNewsDetailView;
    private String mShageUrl;

    public NewsDetailPresenter(Context context, NewsDetailActivity newsDetailView) {
        super(context);
        mNewsDetailView = newsDetailView;
        mNewsModel = new NewsModel(getContext());
    }

    public void getNewsData(String id) {
        mNewsModel.getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsDetailBean -> {
                    mShageUrl = newsDetailBean.share_url;
                    mNewsDetailView.showToolBar(newsDetailBean.title);
                    mNewsDetailView.showHeader(newsDetailBean);
                    mNewsDetailView.showWebView(handleHtml(newsDetailBean));
                });
    }

    private String handleHtml(NewsDetailBean newsDetailBean) {
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + newsDetailBean.css.get(0) + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        return String.format("%s%s", head, newsDetailBean.body.replace(img, " "));
    }

    public void shareNews() {
        mNewsDetailView.showShareView(mShageUrl);
    }
}
