package com.maye.today.today.network;

import com.maye.today.today.network.api.LoginServer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrifit使用工具合集
 */
public class RetrofitUtil {

    private static LoginServer loginServer;

    /**
     * 获取Retrofit中Login部分API
     * @return
     */
    public static LoginServer loginServer() {
        if (loginServer() == null) {
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("").
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    build();

           loginServer = retrofit.create(LoginServer.class);

        }
        return loginServer;
    }


}
