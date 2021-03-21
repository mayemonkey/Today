package com.maye.today.record;

import com.maye.today.domain.Record;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public interface RecordModel {

    Observable<String> getRecordCount(String username);

    Observable<List<Record>> getRecord(String username, int start);

    Observable<List<Record>> getRecordByDay(String username, String datetime);

    Observable<List<Record>> getRecordByAssignTime(String username, int type, String time, int start);

}
