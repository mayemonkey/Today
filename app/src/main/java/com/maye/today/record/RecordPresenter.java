package com.maye.today.record;

public interface RecordPresenter {

    void showRecordCount(String username);

    void showAllRecord(String username, int start);

    void showRecordByDay(String username, String datetime);

    void showRecordByAssignTime(String username, int type, String time, int start);

    void onDestroyView();
}
