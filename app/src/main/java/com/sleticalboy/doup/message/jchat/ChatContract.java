package com.sleticalboy.doup.message.jchat;

import com.sleticalboy.base.IBaseView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface ChatContract {

    interface IChatView extends IBaseView {

    }

    /*
    * 1，获取要发送的消息（文本，emoji，图片，音频，视频，位置）
    * 2，发送消息
    * 3，刷新界面
    *
    * 5，获取最新消息
    * 6，刷新界面
    */
    interface IChatPresenter {
    }
}
