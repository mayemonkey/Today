package com.maye.today.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maye.today.domain.Record;
import com.maye.today.today.R;
import com.maye.today.util.CalendarUtil;

import java.util.List;


public class TimelineAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {

    public TimelineAdapter( List<Record> data) {
        super(R.layout.layout_lv_timeline, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, Record record) {
        int i = CalendarUtil.compareDate(record.getDate(), "2011-05-08");

        if (i >= 0){    //未完成
            helper.setBackgroundRes(R.id.iv_line_point, R.drawable.shape_home_circle_blue);
        }else {         //已完成
            helper.setBackgroundRes(R.id.iv_line_point, R.drawable.shape_home_circle_purple);
        }

        helper.setText(R.id.tv_line_date, record.getDate());
        helper.setText(R.id.tv_line_title, record.getTitle());
    }
}
