package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

public interface GroupPresenter {

    void loadGroup(String date, String type);

    void addGroup(Group group);

    void removeGroup(int id, int position);

    void updateGroup(List<Group> groups);

    void viewDestroy();

}
