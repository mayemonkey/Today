package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maye.today.today.R;
import com.zc.today.monkeycalendar.adapter.MonkeyCalendar;

import java.util.Calendar;

/**
 * 主页显示Fragment
 */
public class CalendarFragment extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_calendar, null);
        MonkeyCalendar mc_home = (MonkeyCalendar) view.findViewById(R.id.mc_home);

        mc_home.setOnDateSelectedListener(new MonkeyCalendar.OnDateSelectedListener() {
            public void onDateSelected(Calendar date) {
                long i1 = date.get(Calendar.MONTH) + 1;
                long i = date.get(Calendar.DAY_OF_MONTH);
                Toast.makeText(getContext(), i1 +"-"+ i, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
