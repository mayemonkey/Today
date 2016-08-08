package com.maye.today.network;

import com.maye.today.network.api.LoginServer;
import com.maye.today.network.api.RegisterServer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrifit使用工具合集
 */
public class RetrofitUtil {

    private static LoginServer loginServer;

    private static RegisterServer registerServer;

    /**
     * 获取Retrofit中Login部分API
     *
     * @return
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
     * @return
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

}
