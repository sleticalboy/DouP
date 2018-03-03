package com.sleticalboy.base

import android.content.Context

/**
 * Created by Android Studio.
 * Date: 1/11/18.
 *
 * @author sleticalboy
 */
abstract class BasePresenter<V : IBaseView>(context: Context) {

    var view: V? = null
        private set
    var context: Context? = null
        private set

    init {
        this.context = context
    }

    protected open fun setAdapter() {}

    protected open fun setLayoutManager() {}

    protected open fun initRecyclerView() {}

    protected fun onTokenView(view: V) {
        this.view = view
    }

    protected open fun onUnTokenView() {
        context = null
        view = null
    }
}
