package com.maye.today.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.maye.today.ui.fragment.GroupFragment;
import com.maye.today.ui.fragment.CalendarFragment;
import com.maye.today.ui.fragment.HomeFragment;
import com.maye.today.ui.fragment.OverviewFragment;
import com.maye.today.ui.fragment.TimelineFragment;

/**
 * Fragment创建器
 */
public class FragmentFactory {

    public static Fragment createFragment(String fragmentTag) {
        Fragment fragment = null;

        switch (fragmentTag){
            case "home":
                fragment = new HomeFragment();
                break;

            case "calendar":
                fragment = new CalendarFragment();
                break;

            case "overview":
                fragment = new OverviewFragment();
                break;

            case "group":
                fragment = new GroupFragment();
                break;

            case "timeline":
                fragment = new TimelineFragment();
                break;
        }
        return fragment;
    }


}
