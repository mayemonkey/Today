package com.maye.today.util;

import java.util.Calendar;

public class CalendarUtil {

    /**
     * 比较两个格式为year-month-day的日期数据
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return > 0：date1日期偏后        < 0：date1日期偏前          = 0：year在同日
     */
    public static int compareDate(String date1, String date2) {
        long year1 = Integer.parseInt(date1.split("-")[0]);
        long year2 = Integer.parseInt(date2.split("-")[0]);

        if (year1 - year2 != 0) {
            return (int) (year1 - year2);
        } else {
            long month1 = Integer.parseInt(date1.split("-")[1]);
            long month2 = Integer.parseInt(date2.split("-")[1]);
            if (month1 - month2 != 0) {
                return (int) (month1 - month2);
            } else {
                long day1 = Integer.parseInt(date1.split("-")[2]);
                long day2 = Integer.parseInt(date2.split("-")[2]);
                return (int) (day1 - day2);
            }
        }
    }

    /**
     * 格式化Calendar内部数据：
     * 月份已进行+1处理
     * year-month-day
     */
    public static String formatCalendar(Calendar calendar) {
        if (calendar != null) {
            return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            return null;
        }
    }

}
