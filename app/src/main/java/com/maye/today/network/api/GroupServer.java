package com.maye.today.network.api;


import com.maye.today.domain.Group;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GroupServer {

    @FormUrlEncoded
    @POST("")
    Observable<List<Group>> getGroup(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> updateGroup(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> removeGroup(@Field("id") int id);
}
