package com.sleticalboy.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created on 18-5-24.
 *
 * @author sleticalboy
 * @description
 */
public final class OSUtils {

    public static void handleStatusBar(Activity activity, @ColorRes int statusBarColor,
                                       boolean isNoTitle, boolean isFullScreen) {
        if (isNoTitle) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(statusBarColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // status bar 文字高亮
                int systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (isFullScreen) {
                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                    window.getDecorView().setSystemUiVisibility(systemUiVisibility);
                }
            }
        }
    }
}
