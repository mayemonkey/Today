package com.maye.login.api;


import com.maye.login.bean.LoginResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
