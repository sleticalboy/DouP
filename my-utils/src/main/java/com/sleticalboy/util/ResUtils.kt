package com.sleticalboy.util

import android.content.Context

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

object ResUtils {

    /**
     * 通过 resId 获取字符串
     *
     * @param resId 资源 weatherId
     * @return resId 对应的字符串
     */
    @JvmStatic
    fun getString(context: Context, resId: Int): String {
        return context.resources.getString(resId)
    }
}
