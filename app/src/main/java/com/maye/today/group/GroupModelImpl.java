package com.maye.today.group;

import com.maye.net.NetUtil;
import com.maye.today.domain.Group;
import com.maye.today.network.api.GroupServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

public class GroupModelImpl implements GroupModel {
    @Override
    public Observable<List<Group>> getGroup(String date, String type) {

        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("type", type);
        return NetUtil.createApi(GroupServer.class).getGroup(map);
    }

    @Override
    public Observable<ResponseBody> updateGroup(List<Group> groups) {

        return NetUtil.createApi(GroupServer.class).updateGroup(groups);
    }

    @Override
    public Observable<ResponseBody> removeGroup(int id) {
        return NetUtil.createApi(GroupServer.class).removeGroup(id);
    }

    public Observable<ResponseBody> addGroup(Group group){
        Map<String, String> map = new HashMap<>();
        map.put("date", group.getDate());
        map.put("description", group.getDescription());
        map.put("done", "false");
        return NetUtil.createApi(GroupServer.class).addGroup(map);
    }
}
