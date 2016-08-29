package com.maye.today.network.api;

import com.maye.today.domain.Record;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RecordServer {

    @FormUrlEncoded
    @POST("")
    Observable<List<Record>> getRecord(@Field("username") String username);

    @FormUrlEncoded
    @POST("")
    Observable<List<Record>> getRecordByDay(@FieldMap Map<String, String> map);

}