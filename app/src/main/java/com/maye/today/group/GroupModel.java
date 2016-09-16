package com.maye.today.group;

import com.maye.today.domain.Group;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

public interface GroupModel {

    Observable<List<Group>> getGroup(String date, String type);

    Observable<ResponseBody> updateGroup(Group group);

    Observable<ResponseBody> removeGroup(int id);

}
