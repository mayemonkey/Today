package com.maye.today.setting;

import com.maye.today.domain.User;
import com.maye.today.network.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

public class SettingModelImpl implements SettingModel {

    @Override
    public Observable<ResponseBody> uploadUser(User user) {

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("nickname", user.getNickname());
        map.put("avatar", user.getAvatar());
        map.put("phone", user.getPhone());

        return RetrofitUtil.settingServer().updateSetting(map);
    }

    @Override
    public Observable<ResponseBody> checkPassword(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        return RetrofitUtil.settingServer().updateSetting(map);
    }
}
