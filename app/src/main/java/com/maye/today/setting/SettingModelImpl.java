package com.maye.today.setting;

import com.maye.base.bean.User;
import com.maye.net.NetUtil;
import com.maye.today.network.api.SettingServer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

class SettingModelImpl implements SettingModel {

    @Override
    public Observable<User> getUser(String sessionId) {
        return NetUtil.createApi(SettingServer.class).getUser(sessionId);
    }

    @Override
    public Observable<User> uploadUser(User user) {

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("nickname", user.getNickname());
        map.put("avatar", user.getAvatar());
        map.put("phone", user.getPhone());

        return NetUtil.createApi(SettingServer.class).updateSetting(map);
    }

    @Override
    public Observable<ResponseBody> checkPassword(String username, String password) {

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return NetUtil.createApi(SettingServer.class).checkPassword(map);
    }
}
