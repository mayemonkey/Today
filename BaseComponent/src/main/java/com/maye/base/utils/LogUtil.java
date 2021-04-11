package com.maye.base.utils;

import android.util.Log;

public class LogUtil {

    private boolean isDebug = true;

    public static void LogI(String tag, String text){
        Log.i(tag, text);
    }

    public static void LogE(String tag, String text){
        Log.e(tag, text);
    }

    public static void LogD(String tag, String text){
        Log.d(tag, text);
    }

    public static void LogV(String tag, String text){
        Log.v(tag, text);
    }

    public static void LogW(String tag, String text){
        Log.w(tag, text);
    }
}
