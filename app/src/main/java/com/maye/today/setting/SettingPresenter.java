package com.maye.today.setting;

import com.maye.today.domain.User;

interface SettingPresenter {

    void getUserInfo(String sessionId);

    void updateUserInfo(User user);

    void onViewDestroy();

}
