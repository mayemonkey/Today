package com.maye.today.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.maye.today.ui.fragment.HomeFragment;

/**
 * Fragment创建器
 */
public class FragmentFactory {

    public static Fragment createFragment(int index) {
        Fragment fragment = null;

        switch (index){
            case 0:
                fragment = new HomeFragment();
                break;


        }
        return fragment;
    }


}
