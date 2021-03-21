package com.maye.today.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.maye.today.domain.Record;
import com.maye.today.today.R;

import java.util.List;

public class OverViewAdapter extends BaseQuickAdapter<Record , BaseViewHolder> implements LoadMoreModule {


    public OverViewAdapter(List<Record> data) {
        super(R.layout.layout_lv_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Record record) {
        helper.setText(R.id.tv_lv_record_date, record.getDate());
        helper.setText(R.id.tv_lv_record_description, record.getDescription());
        helper.setText(R.id.tv_lv_record_part, record.getPart());
        helper.setText(R.id.tv_lv_record_time, record.getTime());
        helper.setText(R.id.tv_lv_record_title, record.getTitle());
    }
}
