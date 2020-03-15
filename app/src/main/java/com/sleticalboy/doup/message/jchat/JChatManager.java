package com.sleticalboy.doup.message.jchat;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sleticalboy.doup.BuildConfig;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class JChatManager {

    private static final String TAG = "JChatManager";

    private static final Object sLock = new Object();

    private static JChatManager sInstance;

    private JChatManager() {
    }

    public static JChatManager getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new JChatManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化 JMessage SDK
     */
    public void initialize(@NonNull Context context) {
        initialize(context, false);
    }

    /**
     * 初始化 JMessage SDK
     *
     * @param context    Context 对象
     * @param msgRoaming 是否开启消息漫游，true 表示开启，否则 false
     */
    public void initialize(@NonNull Context context, boolean msgRoaming) {
        if (BuildConfig.DEBUG) {
            JMessageClient.setDebugMode(true);
        } else {
            JMessageClient.setDebugMode(false);
        }
        JMessageClient.init(context, msgRoaming);
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @param callback
     */
    public void getUserInfo(String username, GetUserInfoCallback callback) {
        getUserInfo(username, null, callback);
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @param appKey
     * @param callback
     */
    public void getUserInfo(String username, String appKey, GetUserInfoCallback callback) {
        JMessageClient.getUserInfo(username, appKey, callback);
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param callback
     */
    public void register(String username, String password, BasicCallback callback) {
        register(username, password, null, callback);
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param optionalInfo
     * @param callback
     */
    public void register(String username, String password, RegisterOptionalUserInfo optionalInfo,
                         BasicCallback callback) {
        JMessageClient.register(username, password, optionalInfo, callback);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param callback
     */
    public void login(String username, String password, BasicCallback callback) {
        JMessageClient.login(username, password, callback);
    }

    /**
     * 登出
     */
    public void logout() {
        JMessageClient.logout();
    }
}
