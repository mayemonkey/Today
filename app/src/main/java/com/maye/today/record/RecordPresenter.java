package com.maye.today.record;

public interface RecordPresenter {

    void showAllRecord(String username);

    void showRecordByDay(String username, String datetime);

    void onDestroyView();
}
