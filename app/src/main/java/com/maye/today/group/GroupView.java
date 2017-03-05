package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

public interface GroupView {

    /**
     * 显示Group内容
     */
    void showGroup(List<Group> list);

    /**
     * 显示Toast提示内容
     */
    void showToast(String text);

    /**
     * 刷新指定条目数据
     */
    void notifyItem(int position, Group group);

    /**
     * 移除指定条目
     */
    void removeItem(int position);

    /**
     * 添加条目
     */
    void addItem(Group group);

    void finishActivity();

}
