package com.maye.today.record;

import com.maye.today.domain.Record;

import java.util.List;

import rx.Observable;

public interface RecordModel {

    Observable<List<Record>> getRecord(String username);

    Observable<List<Record>> getRecordByDay(String username, String datetime);

}
