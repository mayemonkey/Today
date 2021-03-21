package com.maye.today.setting;

import com.maye.today.domain.User;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

interface SettingModel {

    Observable<User> getUser(String sessionId);

    Observable<User> uploadUser(User user);

    Observable<ResponseBody> checkPassword(String username, String password);

}
