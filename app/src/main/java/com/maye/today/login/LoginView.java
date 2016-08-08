package com.maye.today.login;

/**
 * LoginView
 */
public interface LoginView {

    /**
     * 输入错误
     *
     * @param index 输入框序号
     * @param error 错误信息
     */
    void inputError(String index, String error);

    /**
     * 显示进度
     */
    void showProgress();

    /**
     * 隐藏进度
     */
    void hideProgress();

    /**
     * 跳转Activity
     */
    void startActivity();

}
