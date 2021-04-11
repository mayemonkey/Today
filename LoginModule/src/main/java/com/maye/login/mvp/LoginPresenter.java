package com.maye.login.mvp;


public interface LoginPresenter {

    void loginCheck(String username, String password);

    void loginInAdvance(String sessionId);

    void onViewDestroy();

}
