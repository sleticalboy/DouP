package com.sleticalboy.base.config;

/**
 * Created by Android Studio.
 * Date: 1/4/18.
 *
 * @author sleticalboy
 */
public interface ConstantValue {

    String URL_PRE = "doup://";

    /**
     * 和风天气背景图
     */
    String KEY_BG = "bing_img";
    /**
     * 和风天气地区
     */
    String KEY_AREA = "area_name";
    /**
     * 和风天气天气预报具体内容
     */
    String KEY_WEATHER = "weather";
    /**
     * 和风天气地区 id
     */
    String KEY_WEATHER_ID = "weather_id";
    /**
     * 第一次进入应用 true or false
     */
    String KEY_FIRST_LAUNCH = "first_launch";
    /**
     * 自动更新天气 true or false
     */
    String KEY_AUTO_UPDATE_WEATHER = "auto_update";
    /**
     * 本地视频 url
     */
    String KEY_LOCAL_VIDEO_URL = "local_video_url";
    /**
     * 视频下载状态  true or false
     */
    String KEY_DOWNLOAD_STATE = "video_download_state";
}
