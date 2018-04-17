package com.mobcb.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作
 *
 * @author lvmenghui
 *         on 2018-03-22
 */

public class DateUtils {

    public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";

    public static final String TYPE_02 = "yyyy-MM-dd";

    public static final String TYPE_03 = "HH:mm:ss";

    public static final String TYPE_04 = "yyyy年MM月dd日";

    public static final String TYPE_05 = "yyyy-MM-dd HH:mm";

    public static final String TYPE_06 = "yyyyMMddHHmmss";

    public static final String TYPE_07 = "yyyy/MM/dd HH:mm";

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss型字符串
     */
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(TYPE_01);
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd型字符串
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(TYPE_02);
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取昨天日期
     *
     * @return yyyy-MM-dd型字符串
     */
    public static String getYestodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(TYPE_02);
        String date = sdf.format(new Date().getTime() - 24 * 60 * 60
                * 1000);
        return date;
    }

    /**
     * 获取时间
     *
     * @return yyyyMMddHH型字符串
     */
    public static String getNowOfYYYYMMDDHH() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String date = sdf.format(new Date().getTime());
        return date;
    }

    /**
     * 根据格式将UNIX时间戳转换为时间字符串
     *
     * @param unixtimestamp
     * @param format
     * @return
     */
    public static String fromUnixTime(long unixtimestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date(unixtimestamp * 1000L));
        return date;
    }

    /**
     * 获取两个日期之间的间隔分钟
     *
     * @return
     */
    public static int getGapMinuteCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) Math.floor(((toCalendar.getTime().getTime() - fromCalendar
                .getTime().getTime()) / (1000 * 60))) + 1;
    }

    /**
     * 将时间字符串转换为时间戳
     * @param timeStr
     * @param pattern
     * @return
     */
    public static long getTimestamp(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(timeStr);
            long tm = d.getTime() / 1000L;   //获得时间戳
            return tm;
        } catch (ParseException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 将时间字符串转换为时间戳
     * @param timeStr
     * @param pattern
     * @return
     */
    public static long getTimeLong(String timeStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(timeStr);
            long tm = d.getTime();   //获得时间戳
            return tm;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String toDateDD(long timestamp) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(TYPE_02);
        result = format.format(new Date(timestamp * 1000));
        return result;
    }
}
