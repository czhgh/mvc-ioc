package com.clement.fruit.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022��12��19��  21:19
 * @Description:
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
