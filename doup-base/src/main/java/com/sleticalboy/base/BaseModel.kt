package com.sleticalboy.base

import android.content.Context

import java.lang.ref.WeakReference

/**
 * <pre>
 * Created by Android Studio.
 *
 * Date: 1/11/18.
</pre> *
 *
 * @author sleticalboy
 */
abstract class BaseModel(context: Context) {

    protected var mContext: WeakReference<Context>? = null

    init {
        mContext = WeakReference(context)
    }

    open fun clear() {
        if (mContext != null) {
            mContext!!.clear()
            mContext = null
        }
    }
}
