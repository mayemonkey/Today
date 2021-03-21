package com.maye.today.global;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class TodayApplication extends Application {

    private static String today;
    private static Context context;
    private static String sessionId;
    private static String username;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        context = this;
    }

    public static String getToday() {
        return today;
    }

    public static void setToday(String todaydate) {
        today = todaydate;
    }

    public static Context getContext(){
        return context;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String id) {
        sessionId = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        TodayApplication.username = username;
    }
}
