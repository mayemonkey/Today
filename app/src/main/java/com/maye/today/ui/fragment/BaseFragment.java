package com.maye.today.ui.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

public abstract class BaseFragment extends Fragment {

    private boolean isViewCreated;
    private boolean isUIVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (isViewCreated && isUIVisible) {
            loadData();
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    protected abstract void loadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }

    public void backgroundAlpha(final Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(lp.alpha, bgAlpha);

        if (bgAlpha == 1) {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,可能出现黑屏的bug
        } else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }

        valueAnimator.setDuration(250).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = (float) animation.getAnimatedValue();
                context.getWindow().setAttributes(lp);
            }
        });

        valueAnimator.start();
    }
}
