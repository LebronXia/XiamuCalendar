package com.riane.xiamucalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.riane.xiamucalendar.CalendarFactory.getMonthOfDayList;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initList();
    }

    private void initList() {
    }

    private void initView() {
        final int[] dateArr= CalendarUtil.getYMD(new Date());
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setAdapter(new CalendarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                if (convertView == null){
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_xiaomi, null);
                }

                TextView chinaText = (TextView) convertView.findViewById(R.id.chinaText);
                TextView text = (TextView) convertView.findViewById(R.id.text);

                text.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    text.setTextColor(0xff9299a1);
                } else {
                    text.setTextColor(0xff444444);
                }
                chinaText.setText(bean.chinaDay);
                return convertView;
            }
        });
        mCalendarView.setData(getMonthOfDayList(dateArr[0],dateArr[1]-Integer.MAX_VALUE/2),true);
    }
}
