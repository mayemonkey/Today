package com.maye.today.register;

import com.maye.today.domain.User;
import com.maye.today.network.RetrofitUtil;


import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * RegisterModel实现
 */
public class RegisterModelImpl implements RegisterModel {

    @Override
    public Observable<ResponseBody> register(User user) {

        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickname());
        map.put("avater", user.getAvatar());
        map.put("password", user.getPassword());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());

        return RetrofitUtil.registerServer().register(map);
    }

}
