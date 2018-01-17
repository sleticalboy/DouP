package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sleticalboy.base.BasePresenter;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class VideoPlayPresenter extends BasePresenter {

    public static final String TAG = "VideoPlayPresenter";

    private IVideoPlayView mVideoPlayView;

    public VideoPlayPresenter(Context context, IVideoPlayView videoPlayView) {
        super(context);
        mVideoPlayView = videoPlayView;
    }

    public void downloadVideo(String url, String saveName) {
        if (!TextUtils.isEmpty(url)) {
            // 下载时保存 url 到 sp，下次直接播放本地文件，节约流量
            RxDownload.getInstance(getContext())
                    .serviceDownload(url, saveName)
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            mVideoPlayView.onStartDownload();
                            // 保存视频链接
//                            SPUtils.putString(ConstantValue.KEY_LOCAL_VIDEO_URL, url);
                            // 保存下载状态
//                            SPUtils.putBoolean(ConstantValue.KEY_DOWNLOAD_STATE, true);
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            mVideoPlayView.onDownloadFinished();
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            mVideoPlayView.onAddTaskError();
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Log.d(TAG, "accept = downloading...");
                        }
                    });
        } else {
            // 链接错误
            mVideoPlayView.onPlayUrlError();
        }
    }
}
