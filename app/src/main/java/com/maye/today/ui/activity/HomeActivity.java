package com.maye.today.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maye.today.global.TodayApplication;
import com.maye.today.today.R;
import com.maye.today.ui.fragment.HomeFragment;
import com.maye.today.ui.fragment.factory.FragmentFactory;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_menu_home;
    private TextView tv_menu_calendar;
    private TextView tv_menu_overview;
    private TextView tv_menu_group;
    private TextView tv_menu_timeline;
    private DrawerLayout dl_home;
    private TextView tv_top_data;
    private TextView tv_menu_setting;

    private ArrayList<Fragment> list_fragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVisualData();

        initComponent();

        initFragment();

    }

    private void initVisualData() {
        TodayApplication.setUsername("mayemonkey");
        TodayApplication.setToday("2016-12-14");
        TodayApplication.setSessionId("11223344");
    }

    /**
     * 包含控件：Toolbar、DrawerLayout、左侧菜单栏、FlameLayout
     */
    private void initComponent() {
        Toolbar tb_home = (Toolbar) findViewById(R.id.tb_home);

        tv_top_data = (TextView) findViewById(R.id.tv_top_data);
        setTitleData(false, "");
        dl_home = (DrawerLayout) findViewById(R.id.dl_home);
        tv_menu_home = (TextView) findViewById(R.id.tv_menu_home);
        tv_menu_calendar = (TextView) findViewById(R.id.tv_menu_calendar);
        tv_menu_overview = (TextView) findViewById(R.id.tv_menu_overview);
        tv_menu_group = (TextView) findViewById(R.id.tv_menu_group);
        tv_menu_timeline = (TextView) findViewById(R.id.tv_menu_timeline);

        tv_menu_setting = (TextView) findViewById(R.id.tv_menu_setting);

        assert tb_home != null;

        //点击事件
        tv_menu_home.setOnClickListener(this);
        tv_menu_calendar.setOnClickListener(this);
        tv_menu_overview.setOnClickListener(this);
        tv_menu_group.setOnClickListener(this);
        tv_menu_timeline.setOnClickListener(this);

        tv_menu_setting.setOnClickListener(this);

        //初始化Toolbar与DrawerLayout
        tb_home.setTitle("Today");// 标题的文字需在setSupportActionBar之前，不然会无效
        tb_home.setTitleTextColor(Color.parseColor("#000000"));

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl_home, tb_home, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        //右侧阴影颜色
        dl_home.setScrimColor(Color.parseColor("#44000000"));
        dl_home.addDrawerListener(actionBarDrawerToggle);
    }

    /**
     * 设定FrameLayout显示内容，修改相应UI显示
     */
    private void initFragment() {
        switchFragment("home");
        tv_menu_home.setSelected(true);
        dl_home.closeDrawers();
    }

    @Override
    public void onClick(View v) {
        resetTextView();
        switch (v.getId()) {
            case R.id.tv_menu_home:
                switchFragment("home");
                tv_menu_home.setSelected(true);
                setTitleData(false, "");
                break;

            case R.id.tv_menu_calendar:
                switchFragment("calendar");
                tv_menu_calendar.setSelected(true);
                break;

            case R.id.tv_menu_overview:
                switchFragment("overview");
                tv_menu_overview.setSelected(true);
                break;

            case R.id.tv_menu_group:
                switchFragment("group");
                tv_menu_group.setSelected(true);
                setTitleData(false, "");
                break;

            case R.id.tv_menu_timeline:
                switchFragment("timeline");
                tv_menu_timeline.setSelected(true);
                setTitleData(false, "");
                break;

            case R.id.tv_menu_setting:
                dl_home.closeDrawers();
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    /**
     * 根据Fragment标签选择在FlameLayout中显示的内容
     * 当要选择的Fragment已经存在于FrameLayout中时，将会直接获取，不再重新实例化。
     *
     * @param fragmentTag 需要显示的Fragment标签
     */
    private void switchFragment(String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏所有Fragment
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null)
                    fragmentTransaction.hide(fragment);
            }
        }

        //显示对应Fragment
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = FragmentFactory.createFragment(fragmentTag);
            fragmentTransaction.add(R.id.fl_home, fragment, fragmentTag).commit();
        } else {
            fragmentTransaction.show(fragment).commit();
            list_fragment.remove(fragment);
        }
        list_fragment.add(fragment);

        dl_home.closeDrawers();
    }

    /**
     * 重置TextViewUI效果
     */
    private void resetTextView() {
        tv_menu_home.setSelected(false);
        tv_menu_calendar.setSelected(false);
        tv_menu_overview.setSelected(false);
        tv_menu_group.setSelected(false);
        tv_menu_timeline.setSelected(false);
    }

    /**
     * 重置TextViewUI效果
     */
    private void resetTextView(String tag) {
        tv_menu_home.setSelected(false);
        tv_menu_calendar.setSelected(false);
        tv_menu_overview.setSelected(false);
        tv_menu_group.setSelected(false);
        tv_menu_timeline.setSelected(false);

        switch (tag){
            case "home":
                tv_menu_home.setSelected(true);
                setTitleData(false, "");
                break;

            case "calendar":
                tv_menu_calendar.setSelected(true);
                break;

            case "overview":
                tv_menu_overview.setSelected(true);
                break;

            case "group":
                tv_menu_group.setSelected(true);
                setTitleData(false, "");
                break;

            case "timeline":
                tv_menu_timeline.setSelected(true);
                setTitleData(false, "");
                break;
        }
    }

    public void setTitleData(boolean visible, String data) {
        tv_top_data.setText(visible ? data : "");
    }

    @Override
    public void onBackPressed() {
        if (list_fragment.size() > 1) {
            Fragment fragment = list_fragment.get(list_fragment.size() - 1);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            list_fragment.remove(fragment);
            Fragment fragment_show = list_fragment.get(list_fragment.size() - 1);
            String tag = fragment_show.getTag();
            resetTextView(tag);
            fragmentTransaction.show(fragment_show).commit();
        } else {
            super.onBackPressed();
        }
    }
}
