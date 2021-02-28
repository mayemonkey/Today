package com.maye.today.monkeytabpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.PagerAdapter;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class TimeAdapter extends PagerAdapter {

    private int count = 0;
    private Context context;
    private List<String> list;

    public TimeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void notifyDataSetChanged() {
        count = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        if (count > 0) {
            count--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.layout_time, null);

        AutofitTextView atv_time = (AutofitTextView) view.findViewById(R.id.atv_time);

        if (list.get(position) != null) {
            atv_time.setText(list.get(position));
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
