package com.maye.today.record;

import com.maye.net.NetUtil;
import com.maye.today.domain.Record;
import com.maye.today.network.api.RecordServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;


public class RecordModelImpl implements RecordModel {

    @Override
    public Observable<String> getRecordCount(String username) {
        return NetUtil.createApi(RecordServer.class).getRecordCount(username);
    }

    @Override
    public Observable<List<Record>> getRecord(String username, int start) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("start", String.valueOf(start));
        return NetUtil.createApi(RecordServer.class).getRecord(map);
    }

    @Override
    public Observable<List<Record>> getRecordByDay(String username, String datetime) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("datetime", datetime);
        return NetUtil.createApi(RecordServer.class).getRecordByDay(map);
    }

    @Override
    public Observable<List<Record>> getRecordByAssignTime(String username, int type, String time, int start) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("type", String.valueOf(type));
        map.put("time", time);
        map.put("start", String.valueOf(start));
        return NetUtil.createApi(RecordServer.class).getRecordByAssignTime(map);
    }

}
