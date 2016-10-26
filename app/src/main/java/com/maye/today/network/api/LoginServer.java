package com.maye.today.network.api;

import com.maye.today.domain.LoginResponse;

import java.util.Map;

import retrofit2.http.Field;
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
    Observable<LoginResponse> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("s")
    Observable<LoginResponse> login(@Field("sessionId") String sessionId);


}
