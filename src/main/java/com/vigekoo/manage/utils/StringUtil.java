package com.vigekoo.manage.utils;


public class StringUtil {

    /**
     * @Author : ljx
     * @Description : 单个字符串判断为空
     * @Param :
     * @Date : 2018/3/13 13:53
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @Author : ljx
     * @Description :多个字符串判断为空
     * @Param :
     * @Date : 2018/3/13 13:53
     */
    public static boolean isBlank(String... str) {
        if (str == null)
            return true;
        for (String s : str) {
            if (s == null || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author : ljx
     * @Description : 单个字符串不为空
     * @Param :
     * @Date : 2018/6/1 15:48
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * @Author : ljx
     * @Description : 多个字符串都不为空
     * @Param :
     * @Date : 2018/6/1 15:49
     */
    public static boolean isNotBlank(String... str) {
        return !isBlank(str);
    }
}