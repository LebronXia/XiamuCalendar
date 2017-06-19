package com.riane.xiamucalendar;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaobozheng on 6/19/2017.
 */

public interface CalendarAdapter {
    View getView(View convertView, ViewGroup parentView, CalendarBean bean);
}
