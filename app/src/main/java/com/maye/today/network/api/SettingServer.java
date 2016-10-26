package com.maye.today.network.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface SettingServer {

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> updateSetting(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> checkPassword(@FieldMap Map<String, String> map);

}
