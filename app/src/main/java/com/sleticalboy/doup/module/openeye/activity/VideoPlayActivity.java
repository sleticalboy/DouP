package com.sleticalboy.doup.module.openeye.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.sleticalboy.annotation.BindView;
import com.sleticalboy.annotation.OnClick;
import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.openeye.VideoBean;
import com.sleticalboy.doup.module.openeye.listener.VideoListener;
import com.sleticalboy.util.CommonUtils;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.StrUtils;
import com.sleticalboy.util.ToastUtils;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class VideoPlayActivity extends BaseActivity implements IVideoPlayView {

    private static final String TAG = "VideoPlayActivity";
    public static final String VIDEO = "video";

    @BindView(R.id.gsy_player)
    private StandardGSYVideoPlayer gsyPlayer;

    @BindView(R.id.img_blurred)
    private ImageView imgBlurred;

    @BindView(R.id.tv_video_title)
    private TextView tvVideoTitle;
    @BindView(R.id.tv_video_time)
    private TextView tvVideoTime;
    @BindView(R.id.tv_video_desc)
    private TextView tvVideoDesc;

    @BindView(R.id.btn_favorite)
    private TextView btnFavorite;
    @BindView(R.id.btn_share)
    private TextView btnShare;
    @BindView(R.id.btn_reply)
    private TextView btnReply;
    @BindView(R.id.btn_cache)
    private TextView btnCache;

    private VideoBean mData;
    private OrientationUtils mOrientationUtils;

    private boolean mIsPause = false;
    private boolean mIsPlay = false;

    private VideoPlayPresenter mPresenter;

    @Override
    protected void beforeViews() {
        Intent intent = getIntent();
        if (intent != null) {
            mData = intent.getParcelableExtra(VIDEO);
        }
    }

    @Override
    protected void initView(final Bundle savedInstanceState) {

        mPresenter = new VideoPlayPresenter(this, this);

        ImageLoader.loadHigh(this, imgBlurred, mData.blurred);

        tvVideoTitle.setText(mData.title);
        tvVideoDesc.setText(mData.desc);
        tvVideoTime.setText(String.format("%s / %s", mData.category, CommonUtils.wrapperTime(mData.duration)));

        btnFavorite.setText(String.valueOf(mData.collectCount));
        btnReply.setText(String.valueOf(mData.replyCount));
        btnShare.setText(String.valueOf(mData.shareCount));
        btnCache.setText(R.string.cache);

        prepareVideo();
    }

    private void prepareVideo() {
        // 查看视频是否缓存过
//        String url = SPUtils.getString(ConstantValue.KEY_LOCAL_VIDEO_URL, null);
        String url = "";
        if (!StrUtils.isEmpty(url)) {
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

    public static void actionStart(Context context, VideoBean videoBean) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO, videoBean);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_cache)
    public void onViewClicked(View view) {
        String url = mData.playUrl;
        String saveName = mData.title;
        mPresenter.downloadVideo(url, saveName);
    }

    public void onPlayUrlError() {
        Toast.makeText(this, "链接错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDownload() {
        ToastUtils.INSTANCE.showToast(this, "开始下载");
    }

    @Override
    public void onDownloadFinished() {
        ToastUtils.INSTANCE.showToast(this, "下载完成");
    }

    @Override
    public void onAddTaskError() {
        ToastUtils.INSTANCE.showToast(this, "添加任务失败");
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
    protected int attachLayout() {
        return R.layout.openeye_activity_play_video;
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

    @Override
    public void onLoad() {

    }

    @Override
    public void onLoadFinished() {

    }

    @Override
    public void onNetError() {

    }
}
