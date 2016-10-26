package com.maye.today.setting;

import com.maye.today.domain.User;

public interface SettingPresenter {

    void checkPasswordAndUpdate(String username, String password, User user);

    void onViewDestroy();

}
