package com.maye.today.network.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 登录Retrofit使用api
 */
public interface LoginServer {

    @FormUrlEncoded
    @POST("s")
    Observable<ResponseBody> login(@FieldMap Map<String, String> map);

}
