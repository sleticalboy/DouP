package com.sleticalboy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import uk.co.senab.photoview.PhotoViewAttacher;

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
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.INSTANCE.showToast(context, "加载失败，请稍后重试");
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        new PhotoViewAttacher(targetView);
                        return false;
                    }
                })
                .bitmapTransform(new BitmapTransformation(context) {
                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        return null;
                    }
                })
                .centerCrop()
                .crossFade()
                .into(targetView);
    }

    public static void clear(View target) {
        if (target == null) {
            throw new NullPointerException("target view is null");
        }
        Glide.clear(target);
    }

    public static void load(Context context, ImageView target, int imgId) {
        if (target == null) {
            throw new NullPointerException("target view is null");
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
            throw new NullPointerException("target view or url is null");
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
            throw new NullPointerException("target view or url is null");
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

    private static void privateLoad(Context context, ImageView target, Object model) {
        final DrawableTypeRequest<Object> drawableTypeRequest = Glide.with(context).load(model);
        drawableTypeRequest.asBitmap();
        drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        drawableTypeRequest.centerCrop();
        drawableTypeRequest.into(target);
    }
}
