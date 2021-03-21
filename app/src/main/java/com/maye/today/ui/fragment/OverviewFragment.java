package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.maye.today.domain.Record;
import com.maye.today.global.TodayApplication;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.activity.HomeActivity;
import com.maye.today.ui.adapter.OverViewAdapter;
import com.maye.today.util.LogUtil;
import com.maye.view.MonkeyDatePager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OverviewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecordView, MonkeyDatePager.OnMonkeyTimeChangedListener, OnLoadMoreListener {

    private View view;

    private List<Record> list = new ArrayList<>();
    private RecordPresenter recordPresenter;

    private MonkeyDatePager mdp_time;

    private static final int REFRESH = 0;
    private static final int LOADMORE = 1;
    private static int request_mode = REFRESH;

    private int start = 0;
    private SwipeRefreshLayout srl_over;
    private OverViewAdapter overViewAdapter;
    private View view_empty;
    private View view_error;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_overview, null);
        recordPresenter = new RecordPresenterImpl(this);
        initComponent();
        onRefresh();
        updateTitleText();
        return view;
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
            updateTitleText();
        }
    }

    private void initComponent() {
        mdp_time =  view.findViewById(R.id.mdp_time);
        mdp_time.setOnMonkeyTimeChangedListener(this);

        srl_over =  view.findViewById(R.id.srl_overview);
        srl_over.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl_over.setOnRefreshListener(this);

        //初始化数据为空时显示View
        view_empty = View.inflate(getContext(), R.layout.view_empty, null);
        view_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        view_error = View.inflate(getContext(), R.layout.view_error, null);
        view_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        //通过Application获取Today时间值
        String today = TodayApplication.getToday();

        //Application中并未存储时间值，使用本地时间
        if (TextUtils.isEmpty(today)) {
            Calendar date = Calendar.getInstance();
            today = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
        }
        LogUtil.LogI("OverviewFragment", "本地时间值为:" + today);

        RecyclerView rv_overview =  view.findViewById(R.id.rv_overview);
        overViewAdapter = new OverViewAdapter(list);
        overViewAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        rv_overview.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_overview.setAdapter(overViewAdapter);
    }

    @Override
    /**
     * 时间变化回调
     */
    public void onTimeChanged(Calendar time) {
        onRefresh();
        updateTitleText();
    }

    @Override
    /**
     * 下拉刷新回调
     */
    public void onRefresh() {
        list.clear();
        start = 0;
        overViewAdapter.getLoadMoreModule().setEnableLoadMore(false);
        recordPresenter.showRecordByAssignTime(TodayApplication.getUsername(), mdp_time.getType(), mdp_time.getInnerTime(), start);
        showRefresh(true);
    }

    /**
     * 加载更多回调
     */
    @Override
    public void onLoadMore() {
        srl_over.setEnabled(false);
        recordPresenter.showRecordByAssignTime(TodayApplication.getUsername(), mdp_time.getType(), mdp_time.getInnerTime(), start);
    }

    @Override
    /**
     * 数据显示流程
     * 重置加载更多UI
     * 如果是刷新状态则移除之前数据，加入新获取数据
     * 移动起始请求位
     * 根据当前数据情况决定数据UI
     */
    public void showRecord(List<Record> list_record) {
        if (request_mode == REFRESH) {       //刷新获得返回结果
            overViewAdapter.getLoadMoreModule().setEnableLoadMore(true);
            if (list_record == null) {
                overViewAdapter.setEmptyView(view_error);
                return;
            }

            list.addAll(list_record);

            if (list.size() == 0) {
                overViewAdapter.setEmptyView(view_empty);
                return;
            }

            overViewAdapter.setNewData(list);
        }

        if (request_mode == LOADMORE) {      //加载更多获取返回结果
            srl_over.setEnabled(true);
            if (list_record == null) {
                overViewAdapter.getLoadMoreModule().loadMoreFail();
                return;
            }

            if (list_record.size() == 0) {
                overViewAdapter.getLoadMoreModule().loadMoreEnd(true);
                return;
            }

            list.addAll(list_record);
            overViewAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }

    @Override
    public void showRefresh(boolean visible) {
        srl_over.setRefreshing(visible);
    }

    /**
     * 根据MonkeyDatePager修改标题栏日期信息
     */
    private void updateTitleText() {
        //设置HomeActivity标题文本
        if (mdp_time != null) {
            String innerTime = mdp_time.getInnerTime();
            ((HomeActivity) getActivity()).setTitleData(true, innerTime);
        }
    }

    @Override
    public void showRecordCount(String count) {
        //DO NOTHING
    }

}
