package com.sleticalboy.doup.message.jpush;

import android.content.Context;

import com.sleticalboy.base.BasePresenter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class PushPresenter extends BasePresenter implements PushContract.IPushPresenter {

    private PushContract.IPushView mPushView;
    private PushMsgAdapterBase mAdapter;

    public PushPresenter(Context context, PushContract.IPushView pushView) {
        super(context);
        mPushView = pushView;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initRecyclerView() {
        mPushView.setLayoutManager();
        mPushView.setAdapter(mAdapter = new PushMsgAdapterBase(getContext()));
    }

    @Override
    public void clickItem(int position) {
        mPushView.showPushDetail(mAdapter.getItem(position));
    }
}
