package com.sleticalboy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class StrUtils {

    /**
     * 校验字符串只能是数字,英文字母和中文
     *
     * @param s 要校验的字符串
     * @return true 符合规则，否则false
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0 || text.toString().trim().length() == 0;
    }

    public static boolean isReadableASCII(CharSequence string) {
        if (isEmpty(string))
            return false;
        try {
            Pattern p = Pattern.compile("[\\x20-\\x7E]+");
            return p.matcher(string).matches();
        } catch (Throwable e) {
            return true;
        }
    }
}
