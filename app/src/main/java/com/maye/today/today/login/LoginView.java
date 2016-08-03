package com.maye.today.today.login;

/**
 * LoginView
 */
public interface LoginView {

    /**
     * 用户名错误
     * @param error
     */
    void usernameError(String error);

    /**
     * 密码错误
     * @param error
     */
    void passwordError(String error);

    /**
     * 显示进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

}
