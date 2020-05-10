package com.maye.today.ui.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maye.satelitelayout.SatelliteLayout;
import com.maye.today.global.TodayApplication;
import com.maye.today.time.TimePresenter;
import com.maye.today.time.TimePresenterImpl;
import com.maye.today.time.TimeView;
import com.maye.today.today.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.Calendar;

public class HomeFragment extends Fragment implements TimeView {

    private TextView tv_year;
    private TextView tv_month;
    private TextView tv_date;
    private TextView tv_day;
    private TextView tv_greeting;
    private TimePresenter timePresenter;
    private String[] weeks = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private SatelliteLayout sl_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);

        initComponent(view);

        timePresenter = new TimePresenterImpl(this);
        timePresenter.getTime();
        return view;
    }


    @Override
    public void onDestroy() {
        if (timePresenter != null) {
            timePresenter.onDestroyView();
        }
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showAnimation();
        }
    }

    private void initComponent(View view) {

        sl_home = view.findViewById(R.id.sl_home);

        tv_year = view.findViewById(R.id.tv_year);
        tv_month = view.findViewById(R.id.tv_month);
        tv_date = view.findViewById(R.id.tv_date);
        tv_day = view.findViewById(R.id.tv_day);
        tv_greeting = view.findViewById(R.id.tv_greeting);
    }

    @Override
    public void showDate(Calendar datetime) {
        int year = datetime.get(Calendar.YEAR);
        int month = datetime.get(Calendar.MONTH) + 1;
        int date = datetime.get(Calendar.DAY_OF_MONTH);
        int day = datetime.get(Calendar.DAY_OF_WEEK) - 1;
        int hour = datetime.get(Calendar.HOUR_OF_DAY);

        tv_year.setText(String.valueOf(year));
        tv_month.setText(String.valueOf(month));
        tv_date.setText(String.valueOf(date));
        tv_day.setText(String.valueOf(weeks[day]));

        if (hour < 12) {
            tv_greeting.setText("早上好");
        } else if (hour < 18) {
            tv_greeting.setText("下午好");
        } else {
            tv_greeting.setText("晚上好");
        }

        TodayApplication.setToday(year + "-" + month + "-" + date);

        showAnimation();
    }


    private void showAnimation() {
        ValueAnimator animator = new ValueAnimator();
        animator.setFloatValues(0, 1.2f, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewHelper.setScaleX(sl_home, value);
                ViewHelper.setScaleY(sl_home, value);
            }
        });
        animator.start();
    }
}
