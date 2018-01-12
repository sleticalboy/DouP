package com.sleticalboy.doup.message;

import android.content.Context;

import com.sleticalboy.doup.base.BasePresenter;
import com.sleticalboy.doup.base.IBaseListView;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class MessagePresenter extends BasePresenter {

    private MessageAdapter mAdapter;
    private IBaseListView mMessageView;
    private MessageModel mMessageModel;

    public MessagePresenter(Context context, IBaseListView messageView) {
        super(context);
        mMessageView = messageView;
        mMessageModel = new MessageModel(context);
    }

    public void setAdapter() {
        mAdapter = new MessageAdapter(getContext());
        mMessageView.getRecyclerView().setAdapter(mAdapter);
    }

    public void getMessage() {
        // 数据层获取 Message
        mMessageModel.getMessages()
                .subscribe(messages -> {
                    mAdapter.addAll(messages);
                });
    }
}
