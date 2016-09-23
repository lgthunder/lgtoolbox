package com.lg.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class IDateFormat {
    public static final String YYMMDD_HHMMSS = "yy-mm-dd hh-mm-ss";

    /**
     * 格式化日期
     *
     * @param timeStr    时间戳 秒
     * @param dataFormat 返回格式
     * @return day
     */
    public static String formatDate(String timeStr, String dataFormat) {
        long time = 0;

        if (null != timeStr) {
            try {
                time = Long.parseLong(timeStr) * 1000;
            } catch (Exception e) {
                time = System.currentTimeMillis();
            }
        } else {
            time = System.currentTimeMillis();
        }
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(date);
    }
}
