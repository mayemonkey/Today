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
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.RecordAdapter;


import java.util.Calendar;
import java.util.List;

/**
 * 主页显示Fragment
 */
public class CalendarFragment extends Fragment implements RecordView{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_calendar, null);

        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        MonkeyCalendar mc_home = (MonkeyCalendar) view.findViewById(R.id.mc_calendar);
        mc_home.setOnDateSelectedListener(new MonkeyCalendar.OnDateSelectedListener() {
            public void onDateSelected(Calendar date) {
            }
        });

        ListView lv_calendar = (ListView) view.findViewById(R.id.lv_calendar);
        RecordAdapter adapter = new RecordAdapter();
        lv_calendar.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void showRecord(List<Record> list) {

    }
}
