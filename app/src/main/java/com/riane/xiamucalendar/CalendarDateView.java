package com.riane.xiamucalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static com.riane.xiamucalendar.CalendarFactory.getMonthOfDayList;

/**
 * Created by xiaobozheng on 6/21/2017.
 */

public class CalendarDateView extends ViewPager implements CalendarTopView{
    
    HashMap<Integer, CalendarView> views = new HashMap<>();
    private LinkedList<CalendarView> cache = new LinkedList<>();
    private CalendarView.OnItemClickListener onItemClickListener;
    private int MAXCOUNT = 6;
    
    private int row = 6;
    private CalendarAdapter mAdapter;
    private int calendarItemHeight = 0;
    
    public void setAdapter(CalendarAdapter adapter){
        mAdapter = adapter;
        initData();
    }

    public CalendarDateView(Context context) {
        super(context);
    }

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //设置监听事件
    public void setOnItemClickListener(CalendarView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private void init() {
        //获取当天的年月日
        final int[] dateArr = CalendarUtil.getYMD(new Date());

        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view ==  object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                CalendarView view;

                if(!cache.isEmpty()){
                    //移除第一个item
                    view = cache.removeFirst();
                } else {
                    view = new CalendarView(container.getContext(), row);
                }

                view.setOnItemClickListener(onItemClickListener);
                //设置子View的布局
                view.setAdapter(mAdapter);
                view.setData(getMonthOfDayList(dateArr[0],dateArr[1]+position-Integer.MAX_VALUE/2),position==Integer.MAX_VALUE/2);
                container.addView(view);
                views.put(position, view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                cache.addLast((CalendarView) object);
                views.remove(position);
            }
        });

        //滑动监听
        addOnPageChangeListener(new SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (onItemClickListener != null){
                    CalendarView view = views.get(position);
                    Object[] obs = view.getSelect();
                    onItemClickListener.onItemClick((View)obs[0], (int)obs[1], (CalendarBean) obs[2]);
                }

            }
        });
    }

    private void initData() {
        //设置当前布局
        setCurrentItem(Integer.MAX_VALUE/2, false);
        getAdapter().notifyDataSetChanged();;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int calendarHeight = 0;
        if (getAdapter() != null){
            CalendarView view = (CalendarView) getChildAt(0);
            if (view != null){
                //日历的高度
                calendarHeight = view.getMeasuredHeight();
                //日历列表的高度
                calendarItemHeight = view.getItemHeight();
            }
            //设置布局的高度
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    public int[] getCurrentSelectPositon() {
        //得到当前选中的CurrentItem。
        CalendarView view = views.get(getCurrentItem());
        if (view == null) {
            //
            view = (CalendarView) getChildAt(0);
        }
        if (view != null) {
            //得到View选中的position
            return view.getSelectPostion();
        }
        //4代表什么意思，
        return new int[4];
    }

    @Override
    public int getItemHeight() {
        return calendarItemHeight;
    }
}
