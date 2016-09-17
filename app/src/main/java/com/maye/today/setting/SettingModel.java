package com.maye.today.setting;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Observable;

public interface SettingModel {

    Observable<ResponseBody> uploadUser(User user);

    Observable<ResponseBody> checkPassword(User user);

}
