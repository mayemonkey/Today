package com.maye.today.network.api;

import com.maye.today.domain.Record;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import io.reactivex.rxjava3.core.Observable;

public interface RecordServer {

    @FormUrlEncoded
    @POST("aa")
    Observable<String> getRecordCount(@Field("username") String username);

    @FormUrlEncoded
    @POST("abc")
    Observable<List<Record>> getRecord(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("aa")
    Observable<List<Record>> getRecordByDay(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("aa")
    Observable<List<Record>> getRecordByAssignTime(@FieldMap Map<String, String> map);

}
