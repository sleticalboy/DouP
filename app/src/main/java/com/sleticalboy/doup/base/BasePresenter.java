package com.sleticalboy.doup.base;

import android.content.Context;

/**
 * Created by Android Studio.
 * Date: 1/11/18.
 *
 * @author sleticalboy
 */
public abstract class BasePresenter<V extends IBaseView> {

    private V mView;
    private Context mContext;

    public BasePresenter(Context context) {
        mContext = context;
    }

    protected void onTokenView(V view) {
        mView = view;
    }

    protected void onUnTokenView() {
        mContext = null;
        mView = null;
    }

    public Context getContext() {
        return mContext;
    }

    public V getView() {
        return mView;
    }
}
