package com.maye.today.setting;

import com.maye.base.bean.User;

interface SettingPresenter {

    void getUserInfo(String sessionId);

    void updateUserInfo(User user);

    void onViewDestroy();

}
