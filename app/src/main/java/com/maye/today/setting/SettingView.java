package com.maye.today.setting;

import com.maye.base.bean.User;

public interface SettingView {

    void setUserData(User user);

    void refreshUserData(User user);

    void showProgressDialog(boolean visible, String title, String content);

}
