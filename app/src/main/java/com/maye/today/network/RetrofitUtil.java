package com.maye.today.network;

import com.maye.today.network.api.LoginServer;
import com.maye.today.network.api.RecordServer;
import com.maye.today.network.api.RegisterServer;
import com.maye.today.network.api.TimeServer;

import java.sql.Time;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrifit使用工具合集
 */
public class RetrofitUtil {

    private static LoginServer loginServer;

    private static RegisterServer registerServer;

    private static TimeServer timeServer;

    private static RecordServer recordServer;

    /**
     * 获取Retrofit中Login部分API
     */
    public static LoginServer loginServer() {
        if (loginServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://121.120.15.16/").
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    build();

            loginServer = retrofit.create(LoginServer.class);

        }
        return loginServer;
    }

    /**
     * 获取Retrofit中register部分API
     */
    public static RegisterServer registerServer() {
        if (registerServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("").
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            registerServer = retrofit.create(RegisterServer.class);
        }
        return registerServer;
    }

    /**
     * 获取标准北京时间部分API
     */
    public static TimeServer timeServer() {
        if (timeServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://apis.baidu.com/3023/time/").
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            timeServer = retrofit.create(TimeServer.class);
        }
        return timeServer;
    }

    /**
     * 获取某用户所有记录部分API
     */
    public static RecordServer recordServer() {
        if (recordServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("").
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            recordServer = retrofit.create(RecordServer.class);
        }

        return recordServer;
    }

}
