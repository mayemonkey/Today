package com.maye.today.register;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * RegisterModel接口
 */
public interface RegisterModel {

    Observable<ResponseBody> register(User user);
}
