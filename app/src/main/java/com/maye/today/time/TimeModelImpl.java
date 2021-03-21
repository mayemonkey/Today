package com.maye.today.time;

import com.maye.today.network.RetrofitUtil;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;


public class TimeModelImpl implements TimeModel {

    @Override
    public Observable<ResponseBody> getDatetime(String apikey) {
        return RetrofitUtil.timeServer().getTime(apikey);
    }

}
