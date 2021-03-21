package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

public interface GroupModel {

    Observable<List<Group>> getGroup(String date, String type);

    Observable<ResponseBody> updateGroup(List<Group> groups);

    Observable<ResponseBody> removeGroup(int id);

    Observable<ResponseBody> addGroup(Group group);

}
