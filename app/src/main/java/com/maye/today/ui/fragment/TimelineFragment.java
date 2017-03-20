package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maye.today.domain.Record;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.LineAdapter;
import com.maye.today.view.LoadListView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineFragment extends Fragment implements RecordView, LoadListView.OnLoadMoreListener {

    private TextView tv_line_count;
    private LoadListView llv_line;

    private List<Record> list = new ArrayList<>();
    private LineAdapter adapter;
    private RecordPresenter recordPresenter;

    private int start = 0;

    private boolean isRefresh = true;
    private AVLoadingIndicatorView aiv_timeline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_timeline, null);

        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        tv_line_count = (TextView) view.findViewById(R.id.tv_line_count);
        CircleImageView civ_line_avatar = (CircleImageView) view.findViewById(R.id.civ_line_avatar);
//        Glide.with(getContext()).load("").centerCrop().into(civ_line_avatar);

        aiv_timeline = (AVLoadingIndicatorView) view.findViewById(R.id.aiv_timeline);

        adapter = new LineAdapter(getContext(), list);
        llv_line = (LoadListView) view.findViewById(R.id.llv_line);
        llv_line.setFooterView();
        llv_line.setDividerHeight(0);
        llv_line.setOnLoadMoreListener(this);
        llv_line.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            Record record = new Record();
            record.setDate("2013-04-02");
            record.setTime("07:24");
            record.setPart("AM");
            record.setTitle("TITLE");
            record.setDescription("ABCDEFGHIJKLOPQRST");
            list.add(record);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        recordPresenter = new RecordPresenterImpl(this);
//        recordPresenter.showRecordCount("username");
        showRefresh(true);
        recordPresenter.showAllRecord("username", 0);
    }

    @Override
    public void onLoadMore() {
        recordPresenter.showAllRecord("username", start);
        isRefresh = false;
    }

    @Override
    public void showRecord(List<Record> list_record) {
        llv_line.resetFooterView();

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
        tv_line_count.setText(count);
    }

    @Override
    public void showRefresh(boolean visible) {
        if (visible){
            aiv_timeline.show();
        }else {
            aiv_timeline.hide();
        }
    }

    @Override
    public void onDestroy() {
        recordPresenter.onDestroyView();
        super.onDestroy();
    }
}
