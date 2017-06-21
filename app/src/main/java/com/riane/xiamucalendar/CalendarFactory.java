package com.riane.xiamucalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.riane.xiamucalendar.CalendarUtil.getDayOfWeek;

/**
 * Created by xiaobozheng on 6/19/2017.
 */

public class CalendarFactory {

    private static HashMap<String, List<CalendarBean>> cache = new HashMap<>();

    //获取一月中的集合
    public static List<CalendarBean> getMonthOfDayList(int y, int m) {

        String key = y+""+m;
        if(cache.containsKey(key)){
            List<CalendarBean> list=cache.get(key);
            if(list==null){
                cache.remove(key);
            }else{
                return list;
            }
        }

        List<CalendarBean> list = new ArrayList<CalendarBean>();
        cache.put(key,list);

        //计算出一月第一天是星期几
        int fweek = getDayOfWeek(y, m, 1);
        int total = CalendarUtil.getDayOfMaonth(y, m);

        //根据星期推出前面还有几个显示
        for (int i = fweek - 1; i > 0; i--) {
            CalendarBean bean = geCalendarBean(y, m, 1 - i);
            //表示前面的
            bean.mothFlag = -1;
            list.add(bean);
        }

        //获取当月的天数
        for (int i = 0; i < total; i++) {
            CalendarBean bean = geCalendarBean(y, m, i + 1);
            list.add(bean);
        }

        //为了塞满42个格子，显示多出当月的天数
        //42减去全部天数，减去前面多出来的几个星期，就是剩下来多出的天数
        for (int i = 0; i < 42 - (fweek - 1) - total; i++) {
            //获取到年月日加入到Calendarbean中，
            CalendarBean bean = geCalendarBean(y, m, total + i + 1);
            //表示后面的
            bean.mothFlag = 1;
            list.add(bean);
        }
        return list;
    }


    public static CalendarBean geCalendarBean(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        //设为当月的多少天
        calendar.set(year, month - 1, day);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        //获取每一天的年月日
        CalendarBean bean = new CalendarBean(year, month, day);
        //获取到天是星期几
        bean.week = getDayOfWeek(year, month, day);
        //String[] chinaDate = ChinaDate.getChinaDate(year, month, day);
        //bean.chinaMonth = chinaDate[0];
        //bean.chinaDay = chinaDate[1];

        return bean;
    }

    public static void main(String[] args) {
    }
}
