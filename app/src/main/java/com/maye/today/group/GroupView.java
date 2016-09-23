package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

public interface GroupView {

    void showGroup(List<Group> list);

    void showToast(String text);

    void notifyItem(int position, Group group);

    void removeItem(int position);

    void addItem(Group group);

    void finishActivity();

}
