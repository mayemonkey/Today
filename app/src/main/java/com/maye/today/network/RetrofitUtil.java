package com.maye.today.network;

import com.maye.today.network.api.GroupServer;
import com.maye.today.network.api.LoginServer;
import com.maye.today.network.api.RecordServer;
import com.maye.today.network.api.RegisterServer;
import com.maye.today.network.api.SettingServer;
import com.maye.today.network.api.TimeServer;
import com.maye.today.util.HttpUtil;

import okhttp3.OkHttpClient;
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

    private static GroupServer groupServer;

    private static SettingServer settingServer;

    /**
     * 获取Retrofit中Login部分API
     */
    public static LoginServer loginServer() {
        if (loginServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(AppUrl.login).
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
                    baseUrl(AppUrl.register).
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
                    baseUrl(AppUrl.time).
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
            OkHttpClient okHttpClient = new OkHttpClient();
            OkHttpClient client = okHttpClient
                    .newBuilder()
                    .addInterceptor(HttpUtil.getInterceptor())
                    .addInterceptor(HttpUtil.getLogInterceptor())
                    .addNetworkInterceptor(HttpUtil.getNetWorkInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(AppUrl.record).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(client).
                    build();

            recordServer = retrofit.create(RecordServer.class);
        }

        return recordServer;
    }

    /**
     * 获取分组内容部分API
     */
    public static GroupServer groupServer() {
        if (groupServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(AppUrl.group).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            groupServer = retrofit.create(GroupServer.class);
        }

        return groupServer;
    }

    /**
     * 获取分组内容部分API
     */
    public static SettingServer settingServer() {
        if (groupServer == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(AppUrl.setting).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            settingServer = retrofit.create(SettingServer.class);
        }

        return settingServer;
    }

}
