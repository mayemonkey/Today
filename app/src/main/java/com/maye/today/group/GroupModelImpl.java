package com.maye.today.group;

import com.maye.today.domain.Group;
import com.maye.today.network.RetrofitUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

public class GroupModelImpl implements GroupModel {
    @Override
    public Observable<List<Group>> getGroup(String date, String type) {

        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("type", type);
        return RetrofitUtil.groupServer().getGroup(map);
    }

    @Override
    public Observable<ResponseBody> updateGroup(List<Group> groups) {

        return RetrofitUtil.groupServer().updateGroup(groups);
    }

    @Override
    public Observable<ResponseBody> removeGroup(int id) {
        return RetrofitUtil.groupServer().removeGroup(id);
    }

    public Observable<ResponseBody> addGroup(Group group){
        Map<String, String> map = new HashMap<>();
        map.put("date", group.getDate());
        map.put("description", group.getDescription());
        map.put("done", "false");
        return RetrofitUtil.groupServer().addGroup(map);
    }
}
