package com.maye.today.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.maye.today.today.R;
import com.maye.today.ui.activity.ListActivity;

public class GroupFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_group, null);

        initComponent(view);


        return view;
    }


    private void initComponent(View view) {
        RelativeLayout rl_food = (RelativeLayout) view.findViewById(R.id.rl_food);
        rl_food.setOnClickListener(this);
        RelativeLayout rl_work = (RelativeLayout) view.findViewById(R.id.rl_work);
        rl_work.setOnClickListener(this);
        RelativeLayout rl_vacation = (RelativeLayout) view.findViewById(R.id.rl_vacation);
        rl_vacation.setOnClickListener(this);
        RelativeLayout rl_outside = (RelativeLayout) view.findViewById(R.id.rl_outside);
        rl_outside.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ListActivity.class);
        switch (v.getId()){
            case R.id.rl_food:
                intent.putExtra("type", "food");
                break;

            case R.id.rl_work:
                intent.putExtra("type", "work");
                break;

            case R.id.rl_vacation:
                intent.putExtra("type", "vacation");
                break;

            case R.id.rl_outside:
                intent.putExtra("type", "outside");
                break;
        }
        ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
    }
}
