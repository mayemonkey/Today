package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.maye.today.domain.Record;
import com.maye.today.monkeycalendar.MonkeyCalendar;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.RecordAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 主页显示Fragment
 */
public class CalendarFragment extends Fragment implements RecordView {

    private List<Record> list = new ArrayList<>();
    private RecordPresenter recordPresenter;
    private MonkeyCalendar mc_home;
    private RecordAdapter adapter;
    private String today;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_calendar, null);

        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        recordPresenter = new RecordPresenterImpl(this);

        mc_home = (MonkeyCalendar) view.findViewById(R.id.mc_calendar);
        mc_home.setOnDateSelectedListener(new MonkeyCalendar.OnDateSelectedListener() {
            public void onDateSelected(Calendar date) {
                recordPresenter.showRecordByDay("", formatCalendar(date));
            }
        });

        ListView lv_calendar = (ListView) view.findViewById(R.id.lv_calendar);
        adapter = new RecordAdapter(getContext(), list, today);
        lv_calendar.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        Calendar selectedDate = mc_home.getSelectedDate();
        recordPresenter.showRecordByDay("", formatCalendar(selectedDate));
    }

    /**
     * 格式化Calendar内部数据：
     * 月份已进行+1处理
     * year-month_day
     */
    private String formatCalendar(Calendar calendar) {
        if (calendar != null) {
            return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            return null;
        }
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
    public void onDestroy() {
        super.onDestroy();
        recordPresenter.onDestroyView();
    }
}
