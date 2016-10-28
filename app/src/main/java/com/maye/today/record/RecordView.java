package com.maye.today.record;

import com.maye.today.domain.Record;

import java.util.List;

public interface RecordView {

    void showRecord(List<Record> list);

    void showRecordCount(String count);

    void showToast(String text);

    void invisibleRefresh();

}
