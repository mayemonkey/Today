package com.maye.today.record;

import com.maye.today.domain.Record;
import com.maye.today.network.RetrofitUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class RecordModelImpl implements RecordModel{
    @Override
    public Observable<List<Record>> getRecord(String username) {
        return RetrofitUtil.recordServer().getRecord(username);
    }

    @Override
    public Observable<List<Record>> getRecordByDay(String username, String datetime) {
        Map<String,String> map = new HashMap<>();
        map.put("username", username);
        map.put("datetime", datetime);
        return RetrofitUtil.recordServer().getRecordByDay(map);
    }
}
