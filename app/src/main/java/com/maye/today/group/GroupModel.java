package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

public interface GroupModel {

    Observable<List<Group>> getGroup(String date, String type);

    Observable<ResponseBody> updateGroup(List<Group> groups);

    Observable<ResponseBody> removeGroup(int id);

    Observable<ResponseBody> addGroup(Group group);

}
