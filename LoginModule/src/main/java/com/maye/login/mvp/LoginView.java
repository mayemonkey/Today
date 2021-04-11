package com.maye.login.mvp;

public interface LoginView {

    /**
     * 输入错误
     *
     * @param index 输入框序号
     * @param error 错误信息
     */
    void inputError(String index, String error);

    /**
     * progressDialog 显示与隐藏
     */
    void showProgress(boolean visible);

    /**
     * 跳转Activity
     */
    void startActivity();

}
