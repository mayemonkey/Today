package com.maye.today.register;

import com.maye.today.domain.User;

/**
 * RegisterPresenter接口
 */
public interface RegisterPresenter {

    void checkRegister(User user);

    void onViewDestroy();

}
