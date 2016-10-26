package com.maye.today.login;

import com.maye.today.domain.LoginResponse;

import rx.Observable;

/**
 * LoginModel接口
 */
public interface LoginModel {

    Observable<LoginResponse> login(String username, String password);

    Observable<LoginResponse> login(String sessionId);
}
