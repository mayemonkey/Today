package com.maye.today.time;

import com.maye.today.domain.DateTime;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2016/8/22.
 */
public class TimeModelImpl implements TimeModel {

    @Override
    public Observable<DateTime> getDatetime() {
        Observable<DateTime> observable = Observable.create(new Observable.OnSubscribe<DateTime>() {
            @Override
            public void call(Subscriber<? super DateTime> subscriber) {

            }
        });

        return null;
    }

}
