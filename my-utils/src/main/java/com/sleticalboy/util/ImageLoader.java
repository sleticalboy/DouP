package com.sleticalboy.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class ImageLoader {

    /**
     * 加载可缩放图片
     */
    public static void loadPhotoView(final Context context, final ImageView targetView, String url) {
        if (targetView == null) {
            throw new NullPointerException("targetView view is null");
        }
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(url).apply(options).into(targetView);

    }

    public static void clear(View target) {
        if (target == null) {
            throw new NullPointerException("target view is null");
        }
        // Glide.clear(target);
    }

    public static void load(Context context, ImageView target, int imgId) {
        if (target == null) {
            throw new NullPointerException("target view is null");
        }
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(imgId).apply(options).into(target);
    }

    /**
     * 加载图片
     */
    public static void load(Context context, ImageView target, String url) {
        if (target == null || StrUtils.isEmpty(url))
            throw new NullPointerException("target view or url is null");
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).load(url).apply(options).into(target);
    }

    /**
     * 加载高清图片
     */
    public static void loadHigh(Context context, ImageView target, String url) {
        if (target == null || StrUtils.isEmpty(url)) {
            throw new NullPointerException("target view or url is null");
        }
        final RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).asBitmap().apply(options).into(target);
    }

    private static void privateLoad(Context context, ImageView target, Object model) {
        final RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context).asBitmap().load(model).apply(options).into(target);
    }
}
