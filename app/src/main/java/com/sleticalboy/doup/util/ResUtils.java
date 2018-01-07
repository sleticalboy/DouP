package com.sleticalboy.doup.util;

import com.sleticalboy.doup.DouApp;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class ResUtils {

    /**
     * 通过 resId 获取字符串
     *
     * @param resId 资源 weatherId
     * @return resId 对应的字符串
     */
    public static String getString(int resId) {
        return DouApp.sReference.get().getResources().getString(resId);
    }
}
