package com.maye.today.monkeytabpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class MonkeyTabPager extends FrameLayout implements View.OnClickListener, ViewPager.OnPageChangeListener, NavigationTabStrip.OnTabStripSelectedIndexListener {

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;

    private boolean allowChange = true;

    private Calendar time = Calendar.getInstance();

    public interface OnMonkeyTimeChangedListener {
        void onTimeChanged(Calendar time);
    }

    public OnMonkeyTimeChangedListener onMonkeyTimeChangedListener;

    public void setOnMonkeyTimeChangedListener(OnMonkeyTimeChangedListener listener) {
        this.onMonkeyTimeChangedListener = listener;
    }

    private List<String> list = new ArrayList<>();
    private int type = DAY;
    private ViewPager vp_time;
    private TimeAdapter adapter;

    public MonkeyTabPager(Context context) {
        super(context);
        init(context);
    }

    public MonkeyTabPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MonkeyTabPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        View view = View.inflate(context, R.layout.view_tabpager, this);

        //顶部Tab初始化
        NavigationTabStrip nts_tab = (NavigationTabStrip) view.findViewById(R.id.nts_tab);
        nts_tab.setTabIndex(2);
        nts_tab.setOnTabStripSelectedIndexListener(this);

        initTime(context, view);

        ImageView iv_preview = (ImageView) view.findViewById(R.id.iv_preview);
        iv_preview.setOnClickListener(this);

        ImageView iv_next = (ImageView) view.findViewById(R.id.iv_next);
        iv_next.setOnClickListener(this);
    }

    private void initTime(Context context, View view) {
        //初始化控件中央适配，设定初始类型及时间
        vp_time = (ViewPager) view.findViewById(R.id.vp_time);
        vp_time.addOnPageChangeListener(this);


        adapter = new TimeAdapter(context, list);
        vp_time.setAdapter(adapter);

        setTypeAndData(DAY, list);
    }

    /**
     * 根据类型及List设置显示
     *
     * @param type      类型
     * @param list_data 显示内容
     */
    public void setTypeAndData(int type, List<String> list_data) {
        this.type = type;

        list.clear();
        list.addAll(list_data);
        allowChange = false;
        adapter.notifyDataSetChanged();
        allowChange = true;

        if (type == YEAR) {
            vp_time.setCurrentItem(list.indexOf(String.valueOf(time.get(Calendar.YEAR))));
        }
        if (type == MONTH) {
            vp_time.setCurrentItem(time.get(Calendar.MONTH));
        }
        if (type == DAY) {
            vp_time.setCurrentItem(time.get(Calendar.DAY_OF_MONTH) - 1);
        }
    }

    public int getType() {
        return type;
    }

    public Calendar getTime() {
        return time;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_preview){
                changeCurrentItem(PREVIEW);
        }else if (view.getId() == R.id.iv_next){
                changeCurrentItem(NEXT);
        }
    }

    private static final int PREVIEW = 0;
    private static final int NEXT = 1;

    private void changeCurrentItem(int changeType) {
        long currentItem = vp_time.getCurrentItem();

        switch (changeType) {
            case PREVIEW:
                if (currentItem == 0) {
                    return;
                }
                vp_time.setCurrentItem((int) (currentItem - 1));
                break;

            case NEXT:
                if (currentItem == list.size() - 1) {
                    return;
                }
                vp_time.setCurrentItem((int) (currentItem + 1));
                break;
        }
    }


    /*--ViewPager滑动监听Implements部分--*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (allowChange) {
            String data = list.get(position);
            setTimeTo(data);
            if (onMonkeyTimeChangedListener != null)
                onMonkeyTimeChangedListener.onTimeChanged(time);
        }
    }
    /*----------------------------------*/

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setTimeTo(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }

        int index = Integer.parseInt(data);

        switch (type) {
            case YEAR:
                time.set(Calendar.YEAR, index);
                break;

            case MONTH:
                time.set(Calendar.MONTH, index - 1);
                break;

            case DAY:
                time.set(Calendar.DAY_OF_MONTH, index);
                break;
        }
    }

    /*-----顶部Tab选择监听Implements部分-----*/

    @Override
    public void onStartTabSelected(String title, int index) {

    }

    @Override
    public void onEndTabSelected(String title, int index) {
        List<String> list ;
        switch (index) {
            case 0:
                list = setList(YEAR, time);
                setTypeAndData(YEAR, list);
                break;

            case 1:
                list = setList(MONTH, time);
                setTypeAndData(MONTH, list);
                break;

            case 2:
                list = setList(DAY, time);
                setTypeAndData(DAY, list);
                break;
        }
    }

    /*--------------------------------------*/

    private List<String> setList(int type, Calendar time) {
        List<String> list = new ArrayList<>();
        switch (type) {
            case YEAR:
                for (int i = 0; i < 200; i++) {
                    list.add(String.valueOf(i + 1969));
                }
                break;

            case MONTH:
                for (int i = 0; i < 12; i++) {
                    list.add(String.valueOf(i + 1));
                }
                break;

            case DAY:
                for (int i = 0; i < time.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                    list.add(String.valueOf(i + 1));
                }
                break;
        }
        return list;
    }

}
