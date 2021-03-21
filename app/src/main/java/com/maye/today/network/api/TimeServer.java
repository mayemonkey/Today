package com.maye.today.network.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import io.reactivex.rxjava3.core.Observable;

public interface TimeServer {

    @GET("time")
    Observable<ResponseBody> getTime(@Header("apikey") String apikey);

}
