package com.maye.today.setting;

import com.maye.today.domain.User;

public interface SettingPresenter {

    void updateUser(User user);

    void onViewDestroy();

}
