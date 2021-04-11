package com.maye.register.mvp;


import com.maye.base.bean.User;

/**
 * RegisterPresenter接口
 */
public interface RegisterPresenter {

    void checkRegister(User user);

    void onViewDestroy();

}
