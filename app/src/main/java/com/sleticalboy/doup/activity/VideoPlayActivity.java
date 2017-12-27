package com.sleticalboy.doup.activity;

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
import com.sleticalboy.doup.bean.eye.VideoBean;
import com.sleticalboy.doup.util.ImageLoader;
import com.sleticalboy.doup.util.VideoListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        String url = getIntent().getStringExtra("");
        if (url != null) {
            Log.d(TAG, url);
            gsyPlayer.setUp(url, false, null, null);
        } else {
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
        tvVideoTime.setText(String.format("%s / %s", mData.category, wrapperTime(mData.duration)));

        btnFavorite.setText(String.valueOf(mData.collectCount));
        btnReply.setText(String.valueOf(mData.replyCount));
        btnShare.setText(String.valueOf(mData.shareCount));
        btnCache.setText(R.string.cache);
    }

    /**
     * 将视频时长转换成 12'19" 05'04" 的形式
     */
    private String wrapperTime(int duration) {
        StringBuilder builder = new StringBuilder();
        int minutes = duration / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            if (hours < 24) {
                builder.append(hours).append(":");
            }
            minutes %= 60;
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        } else {
            if (minutes <= 9)
                builder.append(0).append(minutes).append("'");
            else
                builder.append(minutes).append("'");
        }
        int seconds = duration % 60;
        if (seconds <= 9)
            builder.append(0).append(seconds).append("\"");
        else
            builder.append(seconds).append("\"");
        return builder.toString();
    }

    public static void startAction(Context context, VideoBean videoBean) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO, videoBean);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_cache)
    public void onViewClicked() {
        String url = mData.playUrl;
        if (!TextUtils.isEmpty(url)) {
            // 下载视频
            Toast.makeText(this, "下载视频-待完善", Toast.LENGTH_SHORT).show();
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
