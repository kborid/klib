package com.kborid.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 日期格式化工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class DateUtils {

    private static final String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat(FORMAT_PATTERN);
    }

    public static DateFormat getDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String formatDate(long date) {
        return formatDate(FORMAT_PATTERN, date);
    }

    public static String formatDate(String pattern, long date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(FORMAT_PATTERN, date);
    }

    /**
     * 格式化日期
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String formatDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatDate(String date) {
        return formatDate(FORMAT_PATTERN, date);
    }

    public static String formatDate(String pattern, String date) {
        try {
            long tmp = Long.parseLong(date);
            return new SimpleDateFormat(pattern).format(tmp);
        } catch (Exception e) {
            return null;
        }
    }
}
