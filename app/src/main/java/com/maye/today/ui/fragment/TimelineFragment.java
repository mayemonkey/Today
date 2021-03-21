package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.maye.today.domain.Record;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.TimelineAdapter;
import com.maye.today.view.LoadListView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineFragment extends Fragment implements RecordView, LoadListView.OnLoadMoreListener {

    private TextView tv_line_count;

    private List<Record> list = new ArrayList<>();
    private RecordPresenter recordPresenter;

    private int start = 0;

    private boolean isRefresh = true;
    private TimelineAdapter adapter;
    private SwipeRefreshLayout srl_timeline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_timeline, null);

        initComponent(view);

        recordPresenter = new RecordPresenterImpl(this);
//        recordPresenter.showRecordCount("username");
        showRefresh(true);
        recordPresenter.showAllRecord("username", 0);

        return view;
    }

    private void initComponent(View view) {
        tv_line_count =  view.findViewById(R.id.tv_line_count);
        CircleImageView civ_line_avatar =  view.findViewById(R.id.civ_line_avatar);
//        Glide.with(getContext()).load("").centerCrop().into(civ_line_avatar);

        srl_timeline =  view.findViewById(R.id.srl_timeline);

        adapter = new TimelineAdapter(list);

        RecyclerView rv_timeline =  view.findViewById(R.id.rv_timeline);
        rv_timeline.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_timeline.setAdapter(adapter);
        
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

        adapter.setEmptyView(R.layout.view_empty);
    }

    @Override
    public void onLoadMore() {
        recordPresenter.showAllRecord("username", start);
        isRefresh = false;
    }

    @Override
    public void showRecord(List<Record> list_record) {
        if (list_record == null) {
            return;
        }

        if (isRefresh) {
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
        srl_timeline.setRefreshing(visible);
    }

    @Override
    public void onDestroy() {
        recordPresenter.onDestroyView();
        super.onDestroy();
    }
}
