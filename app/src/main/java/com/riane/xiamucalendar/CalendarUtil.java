package com.riane.xiamucalendar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaobozheng on 6/19/2017.
 */

public class CalendarUtil {
    //获取一月的第一天是星期几
    public static int getDayOfWeek(int y, int m, int day) {
        Calendar calendar = Calendar.getInstance();
        //设为当月一号
        calendar.set(y, m - 1, day);
        //后得到那天是星期几
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取一月最大天数
    public static int getDayOfMaonth(int y, int m) {
        Calendar cal = Calendar.getInstance();
        //设为当月的一号
        cal.set(y, m - 1, 1);
        //获取一个月的天数
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    public static int getMothOfMonth(int y, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, 1);
        int dateOfMonth = cal.get(Calendar.MONTH);
        return dateOfMonth + 1;
    }

    //获得当前时间的年月日
    public static int[] getYMD(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DATE)};
    }
}
