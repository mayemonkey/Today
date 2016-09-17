package com.maye.today.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.Toast;

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
        Group group = new Group();
        group.setDescription("hello");
        group.setDone(false);
        list.add(group);

        initComponent();
    }

    private void initComponent() {
        ImageView iv_top = (ImageView) findViewById(R.id.iv_list_head);
        switch (type){
            case "work":
//                iv_top.setBackgroundResource();
                break;

            case "food":
//                iv_top.setBackgroundResource();
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
        adapter = new ListAdapter(this, groupPresenter, list);
        rv_list.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                groupPresenter.removeGroup(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rv_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
