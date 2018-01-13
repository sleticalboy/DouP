package com.sleticalboy.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class ImageLoader {

    public static void load(Context context, ImageView target, int imgId) {
        if (target == null) {
            throw new IllegalArgumentException("target view is null");
        }
        Glide.with(context)
                .load(imgId)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(target);
    }

    /**
     * 加载图片
     */
    public static void load(Context context, ImageView target, String url) {
        if (target == null || StrUtils.isEmpty(url))
            throw new IllegalArgumentException("target view or url is null");
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(target);
    }

    /**
     * 加载高清图片
     */
    public static void loadHigh(Context context, ImageView target, String url) {
        if (target == null || StrUtils.isEmpty(url))
            throw new IllegalArgumentException("target view or url is null");
        Glide.with(context)
                .load(url)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(target);
    }
}
