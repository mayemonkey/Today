package com.maye.today.global;

import android.app.Application;

public class TodayApplication extends Application {

    private static String today;

    public static String getToday() {
        return today;
    }

    public static void setToday(String todaydate) {
        today = todaydate;
    }
}
