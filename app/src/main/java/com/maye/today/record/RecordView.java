package com.maye.today.record;

import com.maye.today.domain.Record;

import java.util.List;

public interface RecordView {

    /**
     * 显示所有Record数据
     * @param list  Record集合
     */
    void showRecord(List<Record> list);

    /**
     * 显示Record数据数量
     * @param count 数量值
     */
    void showRecordCount(String count);

    /**
     * 显示当前的刷新状态
     * @param visible 是否显示
     */
    void showRefresh(boolean visible);

}
