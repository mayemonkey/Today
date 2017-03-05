package com.maye.today.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.maye.today.today.R;
import com.maye.today.ui.activity.ListActivity;

public class GroupFragment extends Fragment implements View.OnClickListener {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_group, null);

        initComponent();

        return view;
    }

    private void initComponent() {
        ImageView iv_food = (ImageView) view.findViewById(R.id.iv_food);
        iv_food.setOnClickListener(this);
        ImageView iv_work = (ImageView) view.findViewById(R.id.iv_work);
        iv_work.setOnClickListener(this);
        ImageView iv_vacation = (ImageView) view.findViewById(R.id.iv_vacation);
        iv_vacation.setOnClickListener(this);
        ImageView iv_outside = (ImageView) view.findViewById(R.id.iv_outside);
        iv_outside.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ListActivity.class);
        switch (v.getId()){
            case R.id.iv_food:
                intent.putExtra("type", "food");
                break;

            case R.id.iv_work:
                intent.putExtra("type", "work");
                break;

            case R.id.iv_vacation:
                intent.putExtra("type", "vacation");
                break;

            case R.id.iv_outside:
                intent.putExtra("type", "outside");
                break;
        }
        startActivity(intent);
//        ActivityTransitionLauncher.with(getActivity()).from(v).launch(intent);
    }
}
