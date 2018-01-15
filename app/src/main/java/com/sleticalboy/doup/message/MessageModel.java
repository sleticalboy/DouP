package com.sleticalboy.doup.message;

import android.content.Context;

import com.sleticalboy.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/11/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MessageModel extends BaseModel {

    public MessageModel(Context context) {
        super(context);
    }

    public Observable<List<MessageBean>> getMessages() {
        // 获取推送的 message 数据
        return null;
    }

    @Override
    public void clear() {
        super.clear();
    }
}
