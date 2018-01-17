package com.sleticalboy.base;

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

    protected void setAdapter() {
    }

    protected void setLayoutManager() {
    }

    public void initRecyclerView() {
    }

    protected void onTokenView(V view) {
        mView = view;
    }

    protected void onUnTokenView() {
        mContext = null;
        mView = null;
    }

    public final Context getContext() {
        return mContext;
    }

    public final V getView() {
        return mView;
    }
}
