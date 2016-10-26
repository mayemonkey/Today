package com.maye.today.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maye.today.domain.Record;
import com.maye.today.today.R;
import com.maye.today.util.CalendarUtil;

import java.util.List;

public class LineAdapter extends BaseAdapter {

    private Context context;
    private List<Record> list;

    public LineAdapter(Context context, List<Record> list){
        this.context = context;
        this.list = list;
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
        if (convertView == null){
            convertView = View.inflate(context, R.layout.layout_lv_timeline, null);
        }

        LineViewHolder holder = LineViewHolder.getInstance(convertView);

        Record record = list.get(position);

        int i = CalendarUtil.compareDate(record.getDate(), "2011-05-08");

        if (i >= 0){    //未完成
            holder.iv_line_point.setBackgroundResource(R.drawable.shape_home_circle_blue);
        }else {         //已完成
            holder.iv_line_point.setBackgroundResource(R.drawable.shape_home_circle_purple);
        }

        holder.tv_line_date.setText(record.getDate());
        holder.tv_line_title.setText(record.getTitle());

        return convertView;
    }

    private static class LineViewHolder{

        ImageView iv_line_point;
        TextView tv_line_date;
        TextView tv_line_title;

        LineViewHolder(View convertView){
            iv_line_point = (ImageView) convertView.findViewById(R.id.iv_line_point);
            tv_line_date = (TextView) convertView.findViewById(R.id.tv_line_date);
            tv_line_title = (TextView) convertView.findViewById(R.id.tv_line_title);
        }

        static LineViewHolder getInstance(View convertView){
            LineViewHolder holder = (LineViewHolder) convertView.getTag();
            if (holder == null){
                holder = new LineViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

}
