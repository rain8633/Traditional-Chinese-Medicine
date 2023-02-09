package com.syxu.yaofang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private final static String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static String SIMPLE_FORMAT = "yyyy-MM-dd";

    private final static String TIME_FORMAT = "HH:mm:ss";

    /**
     * 得到当前日期的年-月-日 时:分:秒
     *
     * @return
     */
    public static String getCurrentDateToString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(FULL_FORMAT);
        return df.format(calendar.getTime());
    }

    public static String getCurrentTimeToString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(calendar.getTime());
    }

    /**
     * 得到当前日期的年-月-日
     *
     * @return
     */
    public static String getCurrentDateSimpleToString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        return df.format(calendar.getTime());
    }

    /**
     * 得到当天到下周的日期
     *
     * @return
     */
    public static String getNextWeekToString() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, +6);
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        return df.format(calendar.getTime());
    }

    /**
     * 得到明天的时间
     *
     * @return
     */
    public static String getNextDayToString() {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        calendar.add(Calendar.DAY_OF_WEEK, +1);
        result = df.format(calendar.getTime());
        return result;
    }

    /**
     * @param num
     * @return
     */
    public static String getNumDayByToday(int num) {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        calendar.add(Calendar.DAY_OF_WEEK, num);
        result = df.format(calendar.getTime());
        return result;

    }


    /**
     * @param num
     * @return
     */
    public static String getNumDayFullByToday(int num) {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(FULL_FORMAT);
        calendar.add(Calendar.DAY_OF_WEEK, num);
        result = df.format(calendar.getTime());
        return result;

    }


    /**
     * 字符串日期转Date类型
     *
     * @param str
     * @return
     */
    public static Date stringToSimpleDate(String str) {
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串日期转Date类型
     *
     * @param str
     * @return
     */
    public static Date stringToFullDate(String str) {
        SimpleDateFormat df = new SimpleDateFormat(FULL_FORMAT);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Date类型转字符串日期
     *
     * @return
     */
    public static String dateToSimpleString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        return df.format(date);
    }

    /**
     * 根据日期得到星期几
     *
     * @param date
     * @return
     */
    public static String getWeekChinese(String date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        try {
            cal.setTime(df.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * FULL转SIMPLE
     *
     * @param str
     * @return
     */
    public static String DateTimeToDate(String str) {
        SimpleDateFormat df1 = new SimpleDateFormat(SIMPLE_FORMAT);
        try {
            SimpleDateFormat df2 = new SimpleDateFormat(SIMPLE_FORMAT);
            return df2.format(df1.parse(str));
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 判断两个时间的大小
     * 是否后面那个大小前面那个 格式 yyyy-MM-dd
     *
     * @return true为是  false为否
     */
    public static boolean judgeDate(String startStr, String endStr) {
        Date startDate = stringToSimpleDate(startStr);
        Date endDate = stringToSimpleDate(endStr);
        if (startDate.before(endDate)) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个时间的大小
     * 是否后面那个大小前面那个 格式 yyyy-MM-dd HH:mm:ss
     *
     * @return true为是  false为否
     */
    public static boolean judgeFullDate(String startStr, String endStr) {
        Date startDate = stringToFullDate(startStr);
        Date endDate = stringToFullDate(endStr);
        if (startDate.before(endDate)) {
            return true;
        }
        return false;
    }

    /**
     * 得到多少天的日期
     * 正数为加  负数为减
     *
     * @param str
     * @param day
     * @return
     */
    public static String getNumAfterDate(String str, int day) {
        Date startDate = stringToSimpleDate(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_WEEK, day);
        return dateToSimpleString(calendar.getTime());
    }

    public static String getNumAfterSimpleDate(String str, int day) {
        Date startDate = stringToSimpleDate(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_WEEK, day);
        return dateToSimpleString(calendar.getTime());
    }

    /**
     * 将UNIXTIME转为普通的时间
     *
     * @param unixTime
     * @return
     */
    public static String unixTimeToString(String unixTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(unixTime));
        return dateToSimpleString(c.getTime());
    }

    /**
     * 将普通的日间转为UNIXTIME
     *
     * @param date
     * @return
     */
    public static String stringToUnixTime(String date) {
        Calendar c = Calendar.getInstance();
        Date date2 = stringToSimpleDate(date);
        c.setTime(date2);
        return c.getTimeInMillis() + "";

    }

    /**
     * 计算两个时间小时差
     *
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static double jisuan(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        return result;
    }


    /**
     * 计算两个时间分钟差
     *
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static double jiminute(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60);
        return result;
    }

    //由出生日期获得年龄
    public static int getAge(String str) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_FORMAT);
        Date birthDay = df.parse(str);
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
//        System.out.println(cal);得到当前Calender信息
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);//将cal设置成出生日期得到出生Calender信息
//        System.out.println(cal);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

}
