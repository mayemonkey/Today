package com.maye.today.group;

import com.maye.today.domain.Group;

public interface GroupPresenter {

    void loadGroup(String date, String type);

    void removeGroup(int id);

    void updateGroup(Group group);

    void viewDestroy();

}
