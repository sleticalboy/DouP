package com.sleticalboy.base;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/11/18.
 * </pre>
 *
 * @author sleticalboy
 */
public abstract class BaseModel {

    protected WeakReference<Context> mContext;

    public BaseModel(Context context) {
        mContext = new WeakReference<>(context);
    }

    public void clear() {
        if (mContext != null) {
            mContext.clear();
            mContext = null;
        }
    }
}
