package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.maye.today.domain.Group;
import com.maye.today.group.GroupPresenter;
import com.maye.today.group.GroupPresenterImpl;
import com.maye.today.group.GroupView;
import com.maye.today.today.R;
import com.maye.today.ui.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity implements GroupView {

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

        //TODO  模拟数据
        type = "work";
        Group group = new Group();
        group.setDescription("hello");
        group.setDone(false);
        list.add(group);

        initComponent();
    }

    private void initComponent() {
        ImageView iv_top = (ImageView) findViewById(R.id.iv_top);
        switch (type) {
            case "work":
                iv_top.setBackgroundResource(R.mipmap.list_top_work);
                break;

            case "food":
                iv_top.setBackgroundResource(R.mipmap.list_top_food);
                break;

            case "vacation":
//                iv_top.setBackgroundResource();
                break;

            case "outside":
//                iv_top.setBackgroundResource();
                break;
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
        if (adapter.isModify()){
            showUpdateDialog();
        }else{
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

    private void showProgressBar(){

    }

}
