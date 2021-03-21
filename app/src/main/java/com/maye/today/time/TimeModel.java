package com.maye.today.time;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;


public interface TimeModel {

    Observable<ResponseBody> getDatetime(String apikey);

}
