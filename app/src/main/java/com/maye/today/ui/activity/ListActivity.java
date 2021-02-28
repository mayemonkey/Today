package com.maye.today.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.maye.today.domain.Group;
import com.maye.today.group.GroupPresenter;
import com.maye.today.group.GroupPresenterImpl;
import com.maye.today.group.GroupView;
import com.maye.today.today.R;
import com.maye.today.transformation.BlurTransformation;
import com.maye.today.ui.activity.base.BaseAppCompatActivity;
import com.maye.today.ui.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseAppCompatActivity implements GroupView {

    private List<Group> list = new ArrayList<>();
    private GroupPresenter groupPresenter;
    private ListAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        Group group = new Group();
        group.setDescription("hello");
        group.setDone(false);
        list.add(group);

        initComponent();
    }

    private void initComponent() {
        //Toolbar设置
        Toolbar tb_list = (Toolbar) findViewById(R.id.tb_list);
        tb_list.setTitle(type.toUpperCase());
        setSupportActionBar(tb_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb_list.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //CollapsingToolbarLayout背景设置
        CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //Top Image
        ImageView iv_top = (ImageView) findViewById(R.id.iv_top);
        Bitmap bitmap = null;
        switch (type) {
            case "food":
                Glide.with(this).load(R.mipmap.list_top_food).bitmapTransform(new BlurTransformation(this, 15, 2)).into(iv_top);
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.list_top_food);
                break;

            case "work":
                Glide.with(this).load(R.mipmap.list_top_work).bitmapTransform(new BlurTransformation(this, 15, 2)).into(iv_top);
                break;

            case "vacation":
//                iv_top.setBackgroundResource();
                break;

            case "outside":
//                iv_top.setBackgroundResource();
                break;
        }

        //截取顶部bitmap主色设置为标题及状态栏颜色
        if (bitmap != null) {
            Palette palette = Palette.from(bitmap).generate();
            int color = palette.getDarkMutedColor(Color.parseColor("#FFFFFF"));
            //设置toolbar颜色
            collapsing_toolbar.setContentScrimColor(color);
            //设置statusBar颜色
            setStatusBarColor(color);
        }


        groupPresenter = new GroupPresenterImpl(this);

        RecyclerView rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setItemAnimator(new DefaultItemAnimator());
        adapter = new ListAdapter(this, list);
        rv_list.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                //remove dialog
                new MaterialDialog.Builder(ListActivity.this).title("警告").content("确定删除该条Item吗？").
                        positiveText("删除").
                        onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //remove
                                groupPresenter.removeGroup(list.get(position).getId(), position);
                            }
                        }).
                        negativeText("取消").
                        onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }).show();
            }
        }).attachToRecyclerView(rv_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO　
//        groupPresenter.loadGroup("", "");
    }

    @Override
    public void showGroup(List<Group> groups) {
        if (groups != null && groups.size() > 0) {
            list.clear();
            list.addAll(groups);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyItem(int position, Group group) {
        list.set(position, group);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void addItem(Group group) {
        list.add(0, group);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (adapter.isModify()) {
            showUpdateDialog();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 显示更新弹窗
     */
    private void showUpdateDialog() {
        new MaterialDialog.Builder(this).title("提示").
                content("Today计划发生改变，确定要修改吗？").
                positiveText("确定").
                onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        groupPresenter.updateGroup(list);
                    }
                }).
                negativeText("取消").
                onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }).show();
    }

}
