package com.maye.today.time;

import okhttp3.ResponseBody;
import rx.Observable;


public interface TimeModel {

    Observable<ResponseBody> getDatetime(String apikey);

}
