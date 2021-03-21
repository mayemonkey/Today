package com.maye.today.network.api;

import com.maye.today.domain.Group;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GroupServer {

    @FormUrlEncoded
    @POST("")
    Observable<List<Group>> getGroup(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> updateGroup(@Body List<Group> groups);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> removeGroup(@Field("id") int id);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> addGroup(@FieldMap Map<String, String> map);
}
