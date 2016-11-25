package com.maye.today.register;

/**
 * Register页面View变化接口
 */
public interface RegisterView {

    /**
     * 输入错误
     *
     * @param index 错误序号
     * @param error 错误内容
     */
    void inputError(String index, String error);

    /**
     * Progress显示设置
     * @param visible
     */
    void showProgress(boolean visible);

    /**
     * 开启Activity
     */
    void startActivity();

}
