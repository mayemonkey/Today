package com.maye.today.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.maye.today.today.R;

public class HomeActivity extends AppCompatActivity {

    private Toolbar tb_home;
    private FrameLayout fl_home;
    private DrawerLayout dl_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponent();
    }

    /**
     * 初始化控件
     */
    private void initComponent() {
        tb_home = (Toolbar) findViewById(R.id.tb_home);
        dl_home = (DrawerLayout) findViewById(R.id.dl_home);
        fl_home = (FrameLayout) findViewById(R.id.fl_home);

        tb_home.setTitle("Today");// 标题的文字需在setSupportActionBar之前，不然会无效
        tb_home.setTitleTextColor(Color.parseColor("#000000"));

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl_home, tb_home, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        //右侧阴影颜色
        dl_home.setScrimColor(Color.parseColor("#22000000"));
        dl_home.setDrawerListener(actionBarDrawerToggle);
    }
}
