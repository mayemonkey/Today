package com.maye.net;

/**
 * Retrifit使用工具合集
 */
public class RetrofitUtil {

//    private static LoginServer loginServer;
//
//    private static RegisterServer registerServer;
//
//    private static TimeServer timeServer;
//
//    private static RecordServer recordServer;
//
//    private static GroupServer groupServer;
//
//    private static SettingServer settingServer;
//
//
//
//    /**
//     * 获取Retrofit中Login部分API
//     */
//    public static LoginServer loginServer() {
//        if (loginServer == null) {
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.login).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    build();
//
//            loginServer = retrofit.create(LoginServer.class);
//
//        }
//        return loginServer;
//    }
//
//    /**
//     * 获取Retrofit中register部分API
//     */
//    public static RegisterServer registerServer() {
//        if (registerServer == null) {
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.register).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    build();
//
//            registerServer = retrofit.create(RegisterServer.class);
//        }
//        return registerServer;
//    }
//
//    /**
//     * 获取标准北京时间部分API
//     */
//    public static TimeServer timeServer() {
//        if (timeServer == null) {
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.time).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    build();
//
//            timeServer = retrofit.create(TimeServer.class);
//        }
//        return timeServer;
//    }
//
//    /**
//     * 获取某用户所有记录部分API
//     */
//    public static RecordServer recordServer() {
//        if (recordServer == null) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            OkHttpClient client = okHttpClient
//                    .newBuilder()
//                    .addInterceptor(HttpUtil.getInterceptor())
//                    .addInterceptor(HttpUtil.getLogInterceptor())
//                    .addNetworkInterceptor(HttpUtil.getNetWorkInterceptor())
//                    .build();
//
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.record).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    client(client).
//                    build();
//
//            recordServer = retrofit.create(RecordServer.class);
//        }
//
//        return recordServer;
//    }
//
//    /**
//     * 获取分组内容部分API
//     */
//    public static GroupServer groupServer() {
//        if (groupServer == null) {
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.group).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    build();
//
//            groupServer = retrofit.create(GroupServer.class);
//        }
//
//        return groupServer;
//    }
//
//    /**
//     * 获取分组内容部分API
//     */
//    public static SettingServer settingServer() {
//        if (groupServer == null) {
//            Retrofit retrofit = new Retrofit.Builder().
//                    baseUrl(AppUrl.setting).
//                    addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
//                    addConverterFactory(GsonConverterFactory.create()).
//                    build();
//
//            settingServer = retrofit.create(SettingServer.class);
//        }
//
//        return settingServer;
//    }

}
