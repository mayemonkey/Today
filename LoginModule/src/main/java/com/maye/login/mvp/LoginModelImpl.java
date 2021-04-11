package com.maye.login.mvp;


import com.maye.login.api.LoginServer;
import com.maye.login.bean.LoginResponse;
import com.maye.net.NetUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * LoginModel实现类
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public Observable<LoginResponse> login(String username, String password) {
        //无法操作view，不直接做数据判断，交给Presenter完成

        //登录数据设置
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        return NetUtil.INSTANCE.createApi(LoginServer.class).login(map);
    }

    @Override
    public Observable<LoginResponse> login(String sessionId) {
        return NetUtil.INSTANCE.createApi(LoginServer.class).login(sessionId);
    }
}
