package com.maye.today.time;

import com.maye.today.network.RetrofitUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;


public class TimeModelImpl implements TimeModel {

    @Override
    public Observable<ResponseBody> getDatetime(String apikey) {
        return RetrofitUtil.timeServer().getTime(apikey);
    }

}
