package com.sleticalboy.util

import android.text.TextUtils

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * <pre>
 * Created by Android Studio.
 *
 * Date: 1/13/18.
</pre> *
 *
 * @author sleticalboy
 */
object TimeUtils {

    private var sdf: SimpleDateFormat? = null

    @JvmStatic
    fun formatUTC(l: Long, strPattern: String): String {
        var format = strPattern
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss"
        }
        if (sdf == null) {
            try {
                sdf = SimpleDateFormat(format, Locale.CHINA)
            } catch (e: Throwable) {
            }

        } else {
            sdf!!.applyPattern(format)
        }
        return if (sdf == null) "NULL" else sdf!!.format(l)
    }
}
