package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.maye.today.today.R.id.llv_overview;
import static com.maye.today.util.CalendarUtil.formatCalendar;

/**
 * 主页显示Fragment
 */
public class CalendarFragment extends Fragment implements RecordView {

    private List<Record> list = new ArrayList<>();
    private RecordPresenter recordPresenter;
    private MonkeyCalendar mc_home;
    private RecordAdapter adapter;
    private String today;
    private AVLoadingIndicatorView aiv_load;

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

        Calendar selectedDate = mc_home.getSelectedDate();
        String datetime = CalendarUtil.formatCalendar(selectedDate);
        //请求响应日期Record
        recordPresenter.showRecordByDay(TodayApplication.getUsername(), datetime);
        //设置HomeActivity标题文本
        ((HomeActivity) getActivity()).setTitleData(true, datetime);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity) getActivity()).setTitleData(false, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recordPresenter.onDestroyView();
    }

    private void initComponent(View view) {
        recordPresenter = new RecordPresenterImpl(this);

        mc_home = (MonkeyCalendar) view.findViewById(R.id.mc_calendar);
        mc_home.setOnDateSelectedListener(new MonkeyCalendar.OnDateSelectedListener() {
            public void onDateSelected(Calendar date) {

                String datetime = CalendarUtil.formatCalendar(date);
                //请求响应日期Record
                recordPresenter.showRecordByDay(TodayApplication.getUsername(), datetime);
                //设置HomeActivity标题文本
                ((HomeActivity) getActivity()).setTitleData(true, datetime);
            }
        });

        aiv_load = (AVLoadingIndicatorView) view.findViewById(R.id.aiv_load);

        ListView lv_calendar = (ListView) view.findViewById(R.id.lv_calendar);
        View view_empty = View.inflate(getContext(), R.layout.layout_lv_empty, null);
        lv_calendar.setEmptyView(view_empty);
        adapter = new RecordAdapter(getContext(), list, today, 2);
        lv_calendar.setAdapter(adapter);
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

    @Override
    public void showToast(String text) {
        //DO Nothing
    }

}
