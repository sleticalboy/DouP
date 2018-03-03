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

    fun formatUTC(l: Long, strPattern: String): String {
        var strPattern = strPattern
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss"
        }
        if (sdf == null) {
            try {
                sdf = SimpleDateFormat(strPattern, Locale.CHINA)
            } catch (e: Throwable) {
            }

        } else {
            sdf!!.applyPattern(strPattern)
        }
        return if (sdf == null) "NULL" else sdf!!.format(l)
    }
}
