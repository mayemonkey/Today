package com.maye.today.time;

import com.maye.net.NetUtil;
import com.maye.today.network.api.TimeServer;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;


public class TimeModelImpl implements TimeModel {

    @Override
    public Observable<ResponseBody> getDatetime(String apikey) {
        return NetUtil.createApi(TimeServer.class).getTime(apikey);
    }

}
