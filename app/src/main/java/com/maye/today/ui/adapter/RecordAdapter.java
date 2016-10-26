package com.maye.today.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maye.today.domain.Record;
import com.maye.today.today.R;
import com.maye.today.util.CalendarUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;

    private Context context;
    private List<Record> list;
    private String today;
    private int unit = DAY;

    public RecordAdapter(Context context, List<Record> list, String today, int unit) {
        this.context = context;
        this.list = list;
        this.today = today;
        this.unit = unit;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_lv_record, null);
        }

        Record record = list.get(position);
        RecordViewHolder holder = RecordViewHolder.getInstance(convertView);

        String month = record.getDate().split("-")[1];
        String day = record.getDate().split("-")[2];
        String date_text = month + "-" + day;

        switch (unit) {
            case YEAR:
                holder.tv_record_date.setVisibility(View.VISIBLE);
                holder.tv_record_date.setText(date_text);
                break;

            case MONTH:
                holder.tv_record_date.setVisibility(View.VISIBLE);
                holder.tv_record_date.setText(day);
                break;

            case DAY:
                holder.tv_record_date.setVisibility(View.GONE);
                break;
        }

        holder.tv_record_time.setText(record.getTime());
        holder.tv_record_part.setText(record.getPart());
        holder.tv_record_title.setText(record.getTitle());
        holder.tv_record_description.setText(record.getDescription());

        String date = record.getDate();
        int type = CalendarUtil.compareDate(date, today);
        if (type == 0) {         //当天
            holder.iv_record_point.setBackgroundResource(R.mipmap.icon_point_white);
        } else if (type > 0) {    //当天之后
            holder.iv_record_point.setBackgroundResource(R.mipmap.icon_point_white);
        } else if (type < 0) {    //当天之前
            holder.iv_record_point.setBackgroundResource(R.mipmap.icon_point_white);
        }

        return convertView;
    }

    private static class RecordViewHolder {
        TextView tv_record_date;
        TextView tv_record_time;
        TextView tv_record_part;
        TextView tv_record_title;
        TextView tv_record_description;
        ImageView iv_record_point;

        RecordViewHolder(View convertView) {
            tv_record_date = (TextView) convertView.findViewById(R.id.tv_lv_record_date);
            tv_record_time = (TextView) convertView.findViewById(R.id.tv_lv_record_time);
            tv_record_part = (TextView) convertView.findViewById(R.id.tv_lv_record_part);
            tv_record_title = (TextView) convertView.findViewById(R.id.tv_lv_record_title);
            tv_record_description = (TextView) convertView.findViewById(R.id.tv_lv_record_description);
            iv_record_point = (ImageView) convertView.findViewById(R.id.iv_lv_record_point);
        }

        static RecordViewHolder getInstance(View convertView) {
            RecordViewHolder holder = (RecordViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new RecordViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

}
