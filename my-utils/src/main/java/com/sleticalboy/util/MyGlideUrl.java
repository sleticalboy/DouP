package com.sleticalboy.util;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MyGlideUrl extends GlideUrl {

    private String mUrl;

    public MyGlideUrl(String url) {
        super(url);
        mUrl = url;
    }

    @Override
    public String getCacheKey() {
        return getOriginalUrl();
    }

    private String getOriginalUrl() {
        String originalUrl;
        originalUrl = mUrl.replace(mUrl.substring(mUrl.indexOf("&"), mUrl.length() - 1), "");
        if (StrUtils.isEmpty(originalUrl))
            originalUrl = mUrl.replace(mUrl.substring(mUrl.indexOf("?"), mUrl.length() - 1), "");
        return originalUrl;
    }
}
