package com.maye.login.mvp;


import com.maye.login.bean.LoginResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * LoginModel接口
 */
public interface LoginModel {

    Observable<LoginResponse> login(String username, String password);

    Observable<LoginResponse> login(String sessionId);
}
