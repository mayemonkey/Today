package com.maye.today.time;

import rx.Observable;

/**
 * Created by admin on 2016/8/22.
 */
public interface TimeModel {

    Observable<String> getDatetime();

}
