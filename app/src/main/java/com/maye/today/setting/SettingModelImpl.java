package com.maye.today.setting;

import com.maye.today.domain.User;
import com.maye.today.network.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import io.reactivex.rxjava3.core.Observable;

class SettingModelImpl implements SettingModel {

    @Override
    public Observable<User> getUser(String sessionId) {
        return RetrofitUtil.settingServer().getUser(sessionId);
    }

    @Override
    public Observable<User> uploadUser(User user) {

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("nickname", user.getNickname());
        map.put("avatar", user.getAvatar());
        map.put("phone", user.getPhone());

        return RetrofitUtil.settingServer().updateSetting(map);
    }

    @Override
    public Observable<ResponseBody> checkPassword(String username, String password) {

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return RetrofitUtil.settingServer().checkPassword(map);
    }
}
