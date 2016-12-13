package com.maye.today.record;

public interface RecordPresenter {

    /**
     * 显示当前登录用户总Record数量
     * @param username 用户名
     */
    void showRecordCount(String username);

    /**
     * 显示当前登录用户所有Record内容
     * @param username  用户名
     * @param start 记录开始位置索引值
     */
    void showAllRecord(String username, int start);

    /**
     * 依照日期单位显示所有Record内容
     * @param username 用户名
     * @param datetime 日期数据
     */
    void showRecordByDay(String username, String datetime);

    /**
     * 根据指定格式时间显示所有Record内容
     * @param username 用户名
     * @param type 时间格式类型
     * @param time 时间值 YEAR  |  MONTH  | DAY
     * @param start 开始位置索引值
     */
    void showRecordByAssignTime(String username, int type, String time, int start);

    /**
     * 当View完全销毁时，移除引用，防止内存泄漏
     */
    void onDestroyView();
}
