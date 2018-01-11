package com.sleticalboy.doup.base;

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

    protected WeakReference<Context> mReference;

    public BaseModel(Context context) {
        mReference = new WeakReference<>(context);
    }

    public void clear() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }
}
