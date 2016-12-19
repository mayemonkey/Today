package com.maye.today.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.maye.today.domain.Record;

import com.maye.today.global.TodayApplication;
import com.maye.today.record.RecordPresenter;
import com.maye.today.record.RecordPresenterImpl;
import com.maye.today.record.RecordView;
import com.maye.today.today.R;
import com.maye.today.ui.activity.HomeActivity;
import com.maye.today.ui.adapter.RecordAdapter;
import com.maye.today.util.LogUtil;
import com.maye.today.util.ToastUtil;
import com.maye.today.view.LoadListView;
import com.maye.view.MonkeyDatePager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.media.CamcorderProfile.get;


public class OverviewFragment extends Fragment implements LoadListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecordView, MonkeyDatePager.OnMonkeyTimeChangedListener {

    private View view;

    private List<Record> list = new ArrayList<>();
    private RecordAdapter adapter;
    private LoadListView llv_overview;
    private RecordPresenter recordPresenter;

    private MonkeyDatePager mdp_time;

    private boolean isRefresh = true;
    private int start = 0;
    private SwipeRefreshLayout srl_over;
    private View layout_overview_empty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_overview, null);

        initComponent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recordPresenter = new RecordPresenterImpl(this);
        onRefresh();
        updateTitleText();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recordPresenter.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateTitleText();
        }
    }

    private void initComponent() {
        mdp_time = (MonkeyDatePager) view.findViewById(R.id.mdp_time);

        mdp_time.setOnMonkeyTimeChangedListener(this);

        srl_over = (SwipeRefreshLayout) view.findViewById(R.id.srl_overview);
        srl_over.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl_over.setOnRefreshListener(this);

        initEmptyView();

        //通过Application获取Today时间值
        String today = TodayApplication.getToday();
        LogUtil.LogI("OverviewFragment", "TodayApplication中值为:" + today);

        //Application中并未存储时间值，使用本地时间
        if (TextUtils.isEmpty(today)) {
            Calendar date = Calendar.getInstance();
            today = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DAY_OF_MONTH);
            LogUtil.LogI("OverviewFragment", "本地时间值为:" + today);
        }
        adapter = new RecordAdapter(getContext(), list, today, RecordAdapter.DAY);

        llv_overview = (LoadListView) view.findViewById(R.id.llv_overview);
        llv_overview.setFooterView();
        llv_overview.setOnLoadMoreListener(this);
        llv_overview.setAdapter(adapter);
        llv_overview.setVisibility(View.GONE);
    }

    /**
     * 初始化数据为空时显示View
     * @return
     */
    private View initEmptyView() {
        layout_overview_empty = view.findViewById(R.id.layout_overview_empty);
        layout_overview_empty.setVisibility(View.GONE);
        LinearLayout ll_empty_refresh = (LinearLayout) view.findViewById(R.id.ll_empty_refresh);
        ll_empty_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        return view;
    }

    @Override
    /**
     * 下拉刷新回调
     */
    public void onRefresh() {
        String innerTime = mdp_time.getInnerTime();
        recordPresenter.showRecordByAssignTime(TodayApplication.getUsername(), mdp_time.getType(), innerTime, 0);
        isRefresh = true;
        showRefresh(true);
    }

    @Override
    /**
     * 点击加载更多回调
     */
    public void onLoadMore() {
        recordPresenter.showRecordByAssignTime(TodayApplication.getUsername(), mdp_time.getType(), "time", start);
        isRefresh = false;
        showRefresh(false);
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
     * 数据显示流程
     * 重置加载更多UI
     * 如果是刷新状态则移除之前数据，加入新获取数据
     * 移动起始请求位
     * 根据当前数据情况决定数据UI
     */
    public void showRecord(List<Record> list_record) {
        llv_overview.resetFooterView();

        if (isRefresh) {
            list.clear();
        }

        if (list_record != null) {
            list.addAll(list_record);
        }

        start = list.size();

        if (list.size() == 0) {
            layout_overview_empty.setVisibility(View.VISIBLE);
        } else {
            llv_overview.setVisibility(View.VISIBLE);
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showShortToast(getContext(), text);
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
