package com.sleticalboy.util

import android.content.Context
import android.widget.Toast
import java.lang.IllegalArgumentException

/**
 * Created by Android Studio.
 * Date: 10/29/17.
 *
 * @author sleticalboy
 */
object ToastUtils {

    fun showToast(context: Context?, text: CharSequence) {
        if (context == null)
            throw IllegalArgumentException("context was null")
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, resId: Int) {
        showToast(context, context.resources.getString(resId))
    }
}
