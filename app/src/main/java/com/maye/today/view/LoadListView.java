package com.maye.today.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.maye.today.today.R;
import com.wang.avi.AVLoadingIndicatorView;


public class LoadListView extends ListView {

    private OnLoadMoreListener onLoadMoreListener;
    private TextView layout_load;
    private LinearLayout layout_loading;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.onLoadMoreListener = listener;
    }

    public LoadListView(Context context) {
        super(context);
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFooterView() {
        View view_load = View.inflate(getContext(), R.layout.view_loadmore, null);
        layout_load = (TextView) view_load.findViewById(R.id.layout_load);
        layout_loading = (LinearLayout) view_load.findViewById(R.id.layout_loading);
        layout_loading.setVisibility(View.GONE);
        ((AVLoadingIndicatorView) view_load.findViewById(R.id.aiv_load)).show();

        layout_load.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_load.setVisibility(View.GONE);
                layout_loading.setVisibility(View.VISIBLE);
                onLoadMoreListener.onLoadMore();
            }
        });

        addFooterView(view_load);
    }

    public void resetFooterView(){
        layout_load.setVisibility(View.VISIBLE);
        layout_loading.setVisibility(View.GONE);
    }

}
