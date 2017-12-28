package com.sleticalboy.doup.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Android Studio.
 * Date: 10/29/17.
 *
 * @author sleticalboy
 */

public class ToastUtils {

    public static void showToast(Context context, CharSequence text) {
        if (context == null)
            throw new IllegalArgumentException("context was null");
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getString(resId));
    }
}
