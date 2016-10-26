package com.maye.today.setting;

public interface SettingView {

    void showNotifyDialog(boolean visible);

    void showVerifyDialog(boolean visible);

    void showProgressDialog(boolean visible, String title, String content);

}
