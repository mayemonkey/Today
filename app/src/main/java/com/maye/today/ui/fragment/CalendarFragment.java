package com.maye.today.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.maye.today.domain.Record;
import com.maye.today.global.TodayApplication;
import com.maye.today.monkeycalendar.MonkeyCalendar;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.activity.HomeActivity;
import com.maye.today.ui.adapter.RecordAdapter;
import com.maye.today.util.CalendarUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 主页显示Fragment
 */
public class CalendarFragment extends Fragment implements RecordView {

    private List<Record> list = new ArrayList<>();
    private RecordPresenter recordPresenter;
    //    private MonkeyCalendar mc_home;
    private RecordAdapter adapter;
    private String today;
    private AVLoadingIndicatorView aiv_load;
    private Toolbar tb_calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_calendar, null);

        initComponent(view);

        today = TodayApplication.getToday();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        showRefresh(false);

//        Calendar selectedDate = mc_home.getSelectedDate();
//        changeDateAndTitle(selectedDate);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity) getActivity()).setTitleData(false, "");
    }

    @Override
    public void onDestroy() {
        recordPresenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            Calendar selectedDate = mc_home.getSelectedDate();
//            changeDateAndTitle(selectedDate);
        }
    }

    private void initComponent(View view) {
        recordPresenter = new RecordPresenterImpl(this);

        CollapsingToolbarLayout ctl_calendar = (CollapsingToolbarLayout) view.findViewById(R.id.ctl_calendar);
        ctl_calendar.setCollapsedTitleTextColor(Color.parseColor("#FFFFFFFF"));
        ctl_calendar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        ctl_calendar.setTitle(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        CompactCalendarView ccv_calendar = (CompactCalendarView) view.findViewById(R.id.ccv_calendar);
        ccv_calendar.setLocale(TimeZone.getDefault(), Locale.CHINESE);
        ccv_calendar.setUseThreeLetterAbbreviation(true);
        ccv_calendar.shouldSelectFirstDayOfMonthOnScroll(false);


        aiv_load = (AVLoadingIndicatorView) view.findViewById(R.id.aiv_load);

        RecyclerView rv_calendar = (RecyclerView) view.findViewById(R.id.rv_calendar);
        rv_calendar.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecordAdapter(list,  2);
        rv_calendar.setAdapter(adapter);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //数据全部加载完成
                adapter.loadMoreEnd();

                //数据加载成功
                adapter.loadMoreComplete();

                //数据加载失败
                adapter.loadMoreFail();
            }
        }, rv_calendar);

        adapter.setEmptyView(R.layout.view_empty);
    }

    @Override
    public void showRecord(List<Record> list_result) {
        list.clear();
        if (list_result != null) {
            list.addAll(list_result);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showRefresh(boolean visible) {
        if (visible) {
            aiv_load.show();
        } else {
            aiv_load.hide();
        }
    }

    @Override
    public void showRecordCount(String count) {
        //Do Nothing
    }

    /**
     * 修改选中日期到指定Calendar
     * 1、请求新的数据
     * 2、修改标题处日期数据
     *
     * @param date
     */
    private void changeDateAndTitle(Calendar date) {
        String datetime = CalendarUtil.formatCalendar(date);
        //设置HomeActivity标题文本
        ((HomeActivity) getActivity()).setTitleData(true, datetime);
        //请求响应日期Record
        recordPresenter.showRecordByDay(TodayApplication.getUsername(), datetime);
    }

}
