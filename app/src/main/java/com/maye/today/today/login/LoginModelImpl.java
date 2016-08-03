package com.maye.today.today.login;


import com.maye.today.today.network.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * LoginModel实现类
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public Observable<ResponseBody> login(String username, String password) {
        //无法操作view，不直接做数据判断，交给Presenter完成

        //登录数据设置
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);


        return RetrofitUtil.loginServer().login(map);

    }
}
