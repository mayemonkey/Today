package com.maye.today.network.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import io.reactivex.rxjava3.core.Observable;

/**
 * 注册Retrofit使用api
 */
public interface RegisterServer {

    @FormUrlEncoded
    @POST("s")
    Observable<ResponseBody> register(@FieldMap Map<String, String> map);

}
