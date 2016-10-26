package com.maye.today.login;


public interface LoginPresenter {

    void loginCheck(String username, String password);

    void loginInAdvance(String sessionId);

    void onViewDestroy();

}
