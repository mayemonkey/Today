package com.maye.today.register;

import com.maye.today.domain.User;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

/**
 * RegisterModel接口
 */
public interface RegisterModel {

    Observable<ResponseBody> register(User user);
}
