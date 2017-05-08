package com.maye.today.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maye.today.domain.Record;
import com.maye.today.global.TodayApplication;
import com.maye.today.today.R;
import com.maye.today.util.CalendarUtil;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;

    private int unit = DAY;

    public RecordAdapter(List<Record> records, int unit) {
        super(R.layout.layout_lv_record, records);
        this.unit = unit;
    }

    @Override
    protected void convert(BaseViewHolder helper, Record record) {
        String month = record.getDate().split("-")[1];
        String day = record.getDate().split("-")[2];
        String date_text = month + "-" + day;

        switch (unit) {
            case YEAR:
                helper.setVisible(R.id.tv_lv_record_date, true);
                helper.setText(R.id.tv_lv_record_date, date_text);
                break;

            case MONTH:
                helper.setVisible(R.id.tv_lv_record_date, true);
                helper.setText(R.id.tv_lv_record_date, day);
                break;

            case DAY:
                helper.setVisible(R.id.tv_lv_record_date, true);
                helper.setText(R.id.tv_lv_record_date, date_text);
                break;
        }

        helper.setText(R.id.tv_lv_record_time, record.getTime());
        helper.setText(R.id.tv_lv_record_part, record.getPart());
        helper.setText(R.id.tv_lv_record_title, record.getTitle());
        helper.setText(R.id.tv_lv_record_description, record.getDescription());

        String date = record.getDate();
        int type = CalendarUtil.compareDate(date, TodayApplication.getToday());
        if (type == 0) {         //当天
            helper.setImageResource(R.id.iv_lv_record_point, R.mipmap.icon_point_white);
        } else if (type > 0) {    //当天之后
            helper.setImageResource(R.id.iv_lv_record_point, R.mipmap.icon_point_white);
        } else if (type < 0) {    //当天之前
            helper.setImageResource(R.id.iv_lv_record_point, R.mipmap.icon_point_white);
        }
    }
}
