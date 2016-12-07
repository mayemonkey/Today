package com.maye.today.monkeycalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewSwitcher;

import com.zc.today.monkeycalendar.R;


public class MonkeyCalendar extends TableLayout {

    private Context context;
    private Animation mInAnimationLastMonth, mInAnimationNextMonth, mOutAnimationLastMonth, mOutAnimationNextMonth;
    private ArrayList<Calendar> list_record;

    private ViewSwitcher vs_calendar;
    private MonkeyDate mPreviousSelectedDate;
    private Calendar date_now = Calendar.getInstance();
    private Calendar date_selected = Calendar.getInstance();

    private int selected_year;
    private int selected_month;
    private int selected_day;
    private boolean mMoveToNextMonth = false;
    private boolean initFirstPage = true;

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar date);
    }

    private OnMonthChangeListener mMonthChangeListener;

    public void setOnMonthChangeListener(OnMonthChangeListener mMonthChangeListener) {
        this.mMonthChangeListener = mMonthChangeListener;
    }

    public interface OnMonthChangeListener {
        void onMonthChange(Calendar date);
    }

    private OnDateSelectedListener mDateSelectedListener;

    public void setOnDateSelectedListener(OnDateSelectedListener mDateSelectedListener) {
        this.mDateSelectedListener = mDateSelectedListener;
    }

    public MonkeyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public MonkeyCalendar(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);

    }

    /**
     * 初始化控件
     */
    private void init(Context context) {
        View.inflate(context, R.layout.layout_monkeycalendar, this);
        vs_calendar = (ViewSwitcher) findViewById(R.id.vs_calendar);

        date_now.set(Calendar.DAY_OF_MONTH, 1);

        vs_calendar.addView(getCalendarView());
        initFirstPage = false;
        vs_calendar.addView(getCalendarView());

        mInAnimationLastMonth = AnimationUtils.loadAnimation(context, R.anim.slide_left_in);
        mOutAnimationLastMonth = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);
        mInAnimationNextMonth = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
        mOutAnimationNextMonth = AnimationUtils.loadAnimation(context, R.anim.slide_left_out);
    }

    /**
     * 设置日期显示
     */
    private void goToDate(Date date) {
        // TODO Auto-generated method stub
        date_now.setTime(date);
        // set date to the first day of the month, so we can know which day is
        // the first
        // day of the week.
        date_now.set(Calendar.DAY_OF_MONTH, 1);

        if (mMoveToNextMonth) {
            vs_calendar.setInAnimation(mInAnimationNextMonth);
            vs_calendar.setOutAnimation(mOutAnimationNextMonth);
        } else {
            vs_calendar.setInAnimation(mInAnimationLastMonth);
            vs_calendar.setOutAnimation(mOutAnimationLastMonth);
        }

        vs_calendar.showNext();
    }

    public Calendar getSelectedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selected_year);
        calendar.set(Calendar.MONTH, selected_month);
        calendar.set(Calendar.DAY_OF_MONTH, selected_day);
        return calendar;
    }

    /**
     * 获取日历视图
     */
    private View getCalendarView() {
        TableLayout calendar = new TableLayout(context);
        calendar.setStretchAllColumns(true);
        return fillCalendarView(calendar);
    }

    /**
     * 填充日历视图
     */
    private View fillCalendarView(TableLayout calendar) {
        calendar.removeAllViews();

        int firstDayOfWeek, lastMonthDay, nextMonthDay;

        // 获取本月第一天的星期值
        firstDayOfWeek = date_now.get(Calendar.DAY_OF_WEEK) - 1;

        // lastMonth will be used to display last few dates of previous month in
        // the calendar
        Calendar lastMonth = (Calendar) date_now.clone();
        lastMonth.add(Calendar.MONTH, -1);
        Calendar nextMonth = (Calendar) date_now.clone();
        nextMonth.add(Calendar.MONTH, 1);
        lastMonthDay = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH) - firstDayOfWeek + 1;

        // next month starts with day 1
        nextMonthDay = 1;

        TableRow week;
        MonkeyDate date;

        TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        // initialize the day counter to 1, it will be used to display the dates
        // of the month
        int day = 1;
        for (int i = 0; i < 6; i++) {
            // if (day > date_now.getActualMaximum(Calendar.DAY_OF_MONTH))
            // break;
            week = new TableRow(context);
            // this loop is used to fill out the days in the i-th row in the
            // calendar
            for (int j = 0; j < 7; j++) {
                date = new MonkeyDate(context);
                date.setLayoutParams(lp);
                date.setBackgroundColor(Color.parseColor("#6563a4"));
                date.setGravity(Gravity.CENTER);
                date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                date.setTextColor(Color.parseColor("#535353"));
                date.setTypeface(null, Typeface.BOLD);
                date.setTextAppearance(context, R.style.calendar_date_notthis);
                // 设置表示点不可见
                date.setPointVisiable(false);

                // 上个月
                if (j < firstDayOfWeek && day == 1) {
                    // 设置选中的上月时间
                    date.setCalendar(lastMonth);
                    //设置时间
                    date.setYear(lastMonth.get(Calendar.YEAR));
                    date.setMonth(lastMonth.get(Calendar.MONTH));
                    date.setDay(lastMonthDay);
                    // 设置文本
                    date.setText(String.valueOf(lastMonthDay++));
                    date.setOnClickListener(LastMonthListener);
                }
                // 下个月
                else if (day > date_now.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    // 获取选中的下月时间
                    date.setCalendar(nextMonth);
                    //设置时间
                    date.setYear(nextMonth.get(Calendar.YEAR));
                    date.setMonth(nextMonth.get(Calendar.MONTH));
                    date.setDay(nextMonthDay);
                    // 设置文本
                    date.setText(String.valueOf(nextMonthDay++));
                    date.setOnClickListener(NextMonthListener);
                }
                // 本月
                else {
                    date.setTextAppearance(context, R.style.calendar_date_this);
                    // TODO 设置年月日
                    date.setYear(date_now.get(Calendar.YEAR));
                    date.setMonth(date_now.get(Calendar.MONTH));
                    date.setDay(day);
                    date.setTextColor(Color.WHITE);
                    date_now.set(Calendar.DAY_OF_MONTH, day);

                    // 当前日期是否存在记录
                    if (isRecord(date_now)) {
                        date.setPointVisiable(true);
                    } else {
                        date.setPointVisiable(false);
                    }
                    date.setOnClickListener(dayClickedListener);

                    // 今天
                    if (isToday(date_now)) {
                        selected_year = date.getYear();
                        selected_month = date.getMonth();
                        selected_day = date.getDay();
                        if (initFirstPage) {
                            mPreviousSelectedDate = date;
                        }
                        date.setTextColor(Color.parseColor("#D73C10"));
                        if (date_selected.get(Calendar.MONTH) == date_now.get(Calendar.MONTH)
                                && date_selected.get(Calendar.DAY_OF_MONTH) == day) {
                            date.setPointBackground(R.drawable.icon_point_blue);
//                            date.setPadding(0, 8, 0, 8);
                            date.setBackgroundColor(Color.parseColor("#52d3c4"));
                        }
                        // date.setBackgroundResource(R.drawable.background_today);
                    }
                    // 选中
                    else if (date_selected.get(Calendar.MONTH) == date_now.get(Calendar.MONTH)
                            && date_selected.get(Calendar.DAY_OF_MONTH) == day) {
                        mPreviousSelectedDate = date;
                        date.setTextAppearance(context, R.style.calendar_date_this);
                        date.setTextColor(Color.WHITE);
                        date.setPointBackground(R.drawable.icon_point_blue);
//                        date.setPadding(0, 8, 0, 8);
                        date.setBackgroundColor(Color.parseColor("#52d3c4"));
                        // date.setBackgroundResource(R.drawable.background_day_selected);
                    }
                    date.setText(String.valueOf(day++));
                    // if (j == 0) // Sunday
                    // date.setTextColor(Color.parseColor("#D73C10"));
                    // else if (j == 6) // Saturday
                    // date.setTextColor(Color.parseColor("#009EF7"));
                    // else

                }
//                date.setPadding(0, 8, 0, 8);
                week.addView(date);
            }
            calendar.addView(week);
        }

        return calendar;
    }

    private void changeMonth(boolean changeToNext) {
        if (changeToNext) { // 切换至下个月
            date_now.add(Calendar.MONTH, 1);
            date_now.set(Calendar.DAY_OF_MONTH, 1);
            mMoveToNextMonth = true;
            TableLayout calendar = (TableLayout) vs_calendar.getNextView();
            fillCalendarView(calendar);
            goToDate(date_now.getTime());
            if (mMonthChangeListener != null) {
                mMonthChangeListener.onMonthChange(date_now);
            }
        } else { // 切换至上个月
            date_now.add(Calendar.MONTH, -1);
            date_now.set(Calendar.DAY_OF_MONTH, 1);
            mMoveToNextMonth = false;
            TableLayout calendar = (TableLayout) vs_calendar.getNextView();
            fillCalendarView(calendar);
            goToDate(date_now.getTime());
            if (mMonthChangeListener != null) {
                mMonthChangeListener.onMonthChange(date_now);
            }
        }
    }

    /**
     * 设置存在记录的日期集合
     */
    public void setRecordList(ArrayList<Calendar> list_record) {
        this.list_record = list_record;
        TableLayout calendar = (TableLayout) vs_calendar.getNextView();
        date_now.set(Calendar.DAY_OF_MONTH, 1);
        fillCalendarView(calendar);
        vs_calendar.showNext();
    }

    /**
     * 是否是存在记录的日期
     */
    private boolean isRecord(Calendar date) {
        if (list_record != null) {
            for (Calendar record_date : list_record) {
                // 当前日期存在记录
                if (date.get(Calendar.YEAR) == record_date.get(Calendar.YEAR)
                        && date.get(Calendar.MONTH) == record_date.get(Calendar.MONTH)
                        && date.get(Calendar.DAY_OF_MONTH) == record_date.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是今天
     */
    private boolean isToday(Calendar date) {
        Calendar today = Calendar.getInstance();

        return date.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && date.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 方法重载
     */
    private boolean isToday(int yaer, int month, int day) {
        Calendar today = Calendar.getInstance();

        return yaer == today.get(Calendar.YEAR) && month == today.get(Calendar.MONTH)
                && day == today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 上月点击事件
     */
    private OnClickListener LastMonthListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            MonkeyDate md = (MonkeyDate) view;
            date_selected.set(Calendar.YEAR, md.getYear());
            date_selected.set(Calendar.MONTH, md.getMonth());
            date_selected.set(Calendar.DAY_OF_MONTH, md.getDay());

            date_now.add(Calendar.MONTH, -1);
            date_now.set(Calendar.DAY_OF_MONTH, 1);
            mMoveToNextMonth = false;
            TableLayout calendar = (TableLayout) vs_calendar.getNextView();
            fillCalendarView(calendar);
            goToDate(date_now.getTime());
            // 日期选中回调
            if (mMonthChangeListener != null) {
                mMonthChangeListener.onMonthChange(date_now);
            }
            if (mDateSelectedListener != null)
                mDateSelectedListener.onDateSelected(date_selected);
        }
    };

    /**
     * 下月点击事件
     */
    private OnClickListener NextMonthListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            MonkeyDate md = (MonkeyDate) view;
            date_selected.set(Calendar.YEAR, md.getYear());
            date_selected.set(Calendar.MONTH, md.getMonth());
            date_selected.set(Calendar.DAY_OF_MONTH, md.getDay());

            date_now.add(Calendar.MONTH, 1);
            date_now.set(Calendar.DAY_OF_MONTH, 1);
            mMoveToNextMonth = true;
            TableLayout calendar = (TableLayout) vs_calendar.getNextView();
            fillCalendarView(calendar);
            goToDate(date_now.getTime());
            // 日期选中回调
            if (mMonthChangeListener != null) {
                mMonthChangeListener.onMonthChange(date_now);
            }
            if (mDateSelectedListener != null)
                mDateSelectedListener.onDateSelected(date_selected);
        }
    };
    /**
     * 本月日期点击事件
     */
    private OnClickListener dayClickedListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // view.setBackgroundResource(R.drawable.background_day_selected);
            MonkeyDate tv = (MonkeyDate) view;
            Calendar selected = Calendar.getInstance();
            // TODO DEBUG日期
            selected.set(Calendar.YEAR, tv.getYear());
            selected.set(Calendar.MONTH, tv.getMonth());
            selected.set(Calendar.DAY_OF_MONTH, tv.getDay());
            // 设置当前选中日期
            selected_year = tv.getYear();
            selected_month = tv.getMonth();
            selected_day = tv.getDay();
            if (!isToday(selected)) { // 选中的不是当天
                tv.setTextAppearance(context, R.style.calendar_date_selected);
            }
            tv.setPointBackground(R.drawable.icon_point_blue);
//            tv.setPadding(0, 8, 0, 8);
            tv.setBackgroundColor(Color.parseColor("#52d2c4"));
            if (mPreviousSelectedDate != null) {
                if (mPreviousSelectedDate != tv) {
                    try {
                        if (isToday(mPreviousSelectedDate.getYear(), mPreviousSelectedDate.getMonth(),
                                mPreviousSelectedDate.getDay())) {
                            mPreviousSelectedDate.setTextAppearance(context, R.style.calendar_date_this);
                            mPreviousSelectedDate.setBackgroundColor(Color.parseColor("#6563a4"));
                            mPreviousSelectedDate.setTextColor(Color.parseColor("#D73C10"));
                            mPreviousSelectedDate.setPointBackground(R.drawable.icon_point_white);
                        } else {
                            // mPreviousSelectedDate.setBackgroundResource(R.drawable.background_normal_days);
                            mPreviousSelectedDate.setTextAppearance(context, R.style.calendar_date_this);
                            mPreviousSelectedDate.setBackgroundColor(Color.parseColor("#6563a4"));
                            mPreviousSelectedDate.setPointBackground(R.drawable.icon_point_white);
                        }
                    } catch (Exception ex) {
                        // mPreviousSelectedDate.setBackgroundResource(R.drawable.background_normal_days);
                        mPreviousSelectedDate.setTextAppearance(context, R.style.calendar_date_this);
                        mPreviousSelectedDate.setBackgroundColor(Color.parseColor("#6563a4"));
                        mPreviousSelectedDate.setPointBackground(R.drawable.icon_point_white);
                    }
                }
            } else {

            }
            if (mPreviousSelectedDate != null) {
//                mPreviousSelectedDate.setPadding(0, 8, 0, 8);
            }
            // 设置选中的日期
            int selectedDay = Integer.parseInt(((MonkeyDate) view).getText().toString());
            date_selected.set(Calendar.YEAR, date_now.get(Calendar.YEAR));
            date_selected.set(Calendar.MONTH, date_now.get(Calendar.MONTH));
            date_selected.set(Calendar.DAY_OF_MONTH, selectedDay);
            mPreviousSelectedDate = (MonkeyDate) view;
            // 日期选中回调
            if (mDateSelectedListener != null)
                mDateSelectedListener.onDateSelected(date_selected);
        }
    };

    private int startX;
    private int width;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                System.out.println(startX + "");
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                int endX = (int) ev.getX();
                int offset = endX - startX;
                System.out.println(offset + "");
                if (offset > (width / 4)) { // 向右移动，上一月
                    changeMonth(false);
                }
                if (offset < -(width / 4)) { // 向左移动，下一月
                    changeMonth(true);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    //改变日期为
    public void changeDateTo(Calendar calendar) {
        // 同年变化
        if (calendar.get(Calendar.YEAR) == date_now.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) != date_now.get(Calendar.MONTH)) { // 不同月变化
                if (calendar.get(Calendar.MONTH) > date_now.get(Calendar.MONTH)) { // 下月
                    mMoveToNextMonth = true;
                }
                if (calendar.get(Calendar.MONTH) < date_now.get(Calendar.MONTH)) { // 上月
                    mMoveToNextMonth = false;
                }
                date_now = calendar;
                fillCalendarView((TableLayout) vs_calendar.getNextView());
                goToDate(date_now.getTime());
                if (mMonthChangeListener != null) {
                    mMonthChangeListener.onMonthChange(date_now);
                }
            }
        } else { // 不同年
            if (calendar.get(Calendar.YEAR) > date_now.get(Calendar.YEAR)) {
                mMoveToNextMonth = true;
            }
            if (calendar.get(Calendar.YEAR) < date_now.get(Calendar.YEAR)) {
                mMoveToNextMonth = false;
            }
            date_now = calendar;
            fillCalendarView((TableLayout) vs_calendar.getNextView());
            goToDate(date_now.getTime());
            if (mMonthChangeListener != null) {
                mMonthChangeListener.onMonthChange(date_now);
            }
        }
    }

}
