package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maye.today.domain.Record;
import com.maye.today.global.TodayApplication;
import com.maye.today.monkeytabpager.MonkeyTabPager;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.RecordAdapter;
import com.maye.today.view.LoadListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OverviewFragment extends Fragment implements MonkeyTabPager.OnMonkeyTimeChangedListener, LoadListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecordView {

    private View view;

    private List<Record> list = new ArrayList<>();
    private RecordAdapter adapter;
    private LoadListView llv_overview;
    private RecordPresenter recordPresenter;

    private MonkeyTabPager mtp_time;


    private boolean isRefresh = true;
    private int start = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_overview, null);

        initComponent();

        return view;
    }

    private void initComponent() {
        mtp_time = (MonkeyTabPager) view.findViewById(R.id.mtp_time);
        Calendar time = mtp_time.getTime();
        List<String> list_date = new ArrayList<>();
        for (int i = 0; i < time.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            list_date.add(String.valueOf(i + 1));
        }
        mtp_time.setTypeAndData(MonkeyTabPager.DAY, list_date);
        mtp_time.setOnMonkeyTimeChangedListener(this);

        SwipeRefreshLayout srl_over = (SwipeRefreshLayout) view.findViewById(R.id.srl_overview);
        srl_over.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl_over.setOnRefreshListener(this);

        adapter = new RecordAdapter(getContext(), list, "2015-1-7", RecordAdapter.DAY);
        llv_overview = (LoadListView) view.findViewById(R.id.llv_overview);
        llv_overview.setFooterView();
        llv_overview.setOnLoadMoreListener(this);
        llv_overview.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        recordPresenter = new RecordPresenterImpl(this);
        recordPresenter.showRecordByAssignTime("", mtp_time.getType(), "2016-10-10", 0);
    }

    @Override
    public void onRefresh() {
        recordPresenter.showRecordByAssignTime("", mtp_time.getType(), "2011-04-01", 0);
        isRefresh = true;
    }

    @Override
    public void onLoadMore() {
        recordPresenter.showRecordByAssignTime("username", mtp_time.getType(), "time", start);
        isRefresh = false;
    }

    @Override
    public void showRecord(List<Record> list_record) {
        llv_overview.resetFooterView();

        if (list_record == null) {
            return;
        }

        if (isRefresh){
            list.clear();
        }

        list.addAll(list_record);
        start = list.size();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showRecordCount(String count) {
        //DO NOTHING
    }

    @Override
    public void onTimeChanged(Calendar time) {
        start = 0;
        recordPresenter.showRecordByAssignTime("username", mtp_time.getType(), "today", start);
    }

    @Override
    public void showToast(String text) {

    }
}
