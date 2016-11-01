package com.maye.today.setting;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Observable;

interface SettingModel {

    Observable<User> getUser(String sessionId);

    Observable<User> uploadUser(User user);

    Observable<ResponseBody> checkPassword(String username, String password);

}
