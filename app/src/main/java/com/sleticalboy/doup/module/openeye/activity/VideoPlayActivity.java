package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.config.ConstantValue;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.listener.VideoListener;
import com.sleticalboy.util.CommonUtils;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.SPUtils;
import com.sleticalboy.util.StrUtils;
import com.sleticalboy.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class VideoPlayActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlayActivity";

    public static final String VIDEO = "video";

    @BindView(R.id.gsy_player)
    StandardGSYVideoPlayer gsyPlayer;

    @BindView(R.id.img_blurred)
    ImageView imgBlurred;

    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;
    @BindView(R.id.tv_video_desc)
    TextView tvVideoDesc;

    @BindView(R.id.btn_favorite)
    TextView btnFavorite;
    @BindView(R.id.btn_share)
    TextView btnShare;
    @BindView(R.id.btn_reply)
    TextView btnReply;
    @BindView(R.id.btn_cache)
    TextView btnCache;

    private VideoBean mData;
    private OrientationUtils mOrientationUtils;

    private boolean mIsPause = false;
    private boolean mIsPlay = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);

        initData();

        initView();

        prepareVideo();
    }

    private void prepareVideo() {
        // 查看视频是否缓存过
        String url = SPUtils.getString(ConstantValue.KEY_LOCAL_VIDEO_URL, null);
        if (!StrUtils.isEmpty(url)) {
            gsyPlayer.setUp(url, false, null, null);
        } else {
//            SPUtils.putString(ConstantValue.KEY_LOCAL_VIDEO_URL, mData.playUrl);
            gsyPlayer.setUp(mData.playUrl, false, null, null);
        }
        ImageView imgCover = new ImageView(this);
        imgCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.loadHigh(this, imgCover, mData.feed);
        gsyPlayer.setThumbImageView(imgCover);

        gsyPlayer.getTitleTextView().setVisibility(View.GONE);
        gsyPlayer.getBackButton().setVisibility(View.VISIBLE);
        mOrientationUtils = new OrientationUtils(this, gsyPlayer);
        gsyPlayer.setIsTouchWiget(true);

        gsyPlayer.setRotateViewAuto(false);
        gsyPlayer.setLockLand(false);
        gsyPlayer.setShowFullAnimation(false);
        gsyPlayer.setNeedLockFull(true);
        gsyPlayer.getFullscreenButton().setOnClickListener(v -> {
            // 横屏
            mOrientationUtils.resolveByClick();
            // 隐藏 actionBar 和 statusBar
            gsyPlayer.startWindowFullscreen(VideoPlayActivity.this, true, true);
        });

        gsyPlayer.setStandardVideoAllCallBack(new VideoListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                mOrientationUtils.setEnable(true);
                mIsPlay = true;
            }

            @Override
            public void onClickStartError(String url, Object... objects) {

            }

            @Override
            public void onAutoComplete(String url, Object... objects) {

            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                mOrientationUtils.backToProtVideo();
            }
        });
        gsyPlayer.setLockClickListener((view, lock) -> mOrientationUtils.setEnable(!lock));
        gsyPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mData = intent.getParcelableExtra(VIDEO);
            Log.d(TAG, mData.toString());
        }
    }

    private void initView() {
        ImageLoader.loadHigh(this, imgBlurred, mData.blurred);

        tvVideoTitle.setText(mData.title);
        tvVideoDesc.setText(mData.desc);
        tvVideoTime.setText(String.format("%s / %s", mData.category, CommonUtils.wrapperTime(mData.duration)));

        btnFavorite.setText(String.valueOf(mData.collectCount));
        btnReply.setText(String.valueOf(mData.replyCount));
        btnShare.setText(String.valueOf(mData.shareCount));
        btnCache.setText(R.string.cache);
    }

    public static void actionStart(Context context, VideoBean videoBean) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO, videoBean);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_cache)
    public void onViewClicked() {
        String url = mData.playUrl;
        if (!TextUtils.isEmpty(url)) {
            // 下载时保存 url 到 sp，下次直接播放本地文件，节约流量
            RxDownload.getInstance(this)
                    .serviceDownload(mData.playUrl, mData.title)
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            ToastUtils.showToast(VideoPlayActivity.this, "开始下载");
                            SPUtils.putString(ConstantValue.KEY_LOCAL_VIDEO_URL, url);
                            SPUtils.putBoolean(ConstantValue.KEY_DOWNLOAD_STATE, true);
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            ToastUtils.showToast(VideoPlayActivity.this, "下载完成");
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            ToastUtils.showToast(VideoPlayActivity.this, "添加任务失败");
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
            Toast.makeText(this, "链接错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        mOrientationUtils.backToProtVideo();
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsPause = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mIsPlay && !mIsPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsyPlayer.isIfCurrentIsFullscreen()) {
                    gsyPlayer.startWindowFullscreen(this, true, true);
                }
            } else {
                if (gsyPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                mOrientationUtils.setEnable(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        mOrientationUtils.releaseListener();
    }
}
