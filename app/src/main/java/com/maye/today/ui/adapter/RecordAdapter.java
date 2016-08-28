package com.maye.today.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maye.today.domain.Record;
import com.maye.today.today.R;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private List<Record> list;

    private RecordAdapter(Context context, List<Record> list){
        this.context = context;
        this.list  = list;
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
            convertView = View.inflate(context, R.layout.layout_lv_record, null);
        }

        RecordViewHolder holder = RecordViewHolder.getInstance(convertView);






        return convertView;
    }

    static class RecordViewHolder{
        private TextView tv_record_time;
        private TextView tv_record_part;
        private TextView tv_record_title;
        private TextView tv_record_description;
        private ImageView iv_record_point;

        public RecordViewHolder(View convertView){
            TextView tv_record_time = (TextView) convertView.findViewById(R.id.tv_lv_record_time);
            TextView tv_record_part = (TextView) convertView.findViewById(R.id.tv_lv_record_part);
            TextView tv_record_title = (TextView) convertView.findViewById(R.id.tv_lv_record_title);
            TextView tv_record_description = (TextView) convertView.findViewById(R.id.tv_lv_record_description);
            ImageView iv_record_time = (ImageView) convertView.findViewById(R.id.iv_lv_record_point);
        }

        public static RecordViewHolder getInstance(View convertView){
            RecordViewHolder holder = (RecordViewHolder) convertView.getTag();
            if (holder == null){
                holder = new RecordViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }


}
