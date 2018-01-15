package com.sleticalboy.doup.module.openeye.activity;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/16/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface IVideoPlayView {

    /**
     * 播放链接错误
     */
    void onPlayUrlError();

    /**
     * 开始下载
     */
    void onStartDownload();

    /**
     * 下载完成
     */
    void onDownloadFinished();

    /**
     * 添加缓存任务失败
     */
    void onAddTaskError();
}
