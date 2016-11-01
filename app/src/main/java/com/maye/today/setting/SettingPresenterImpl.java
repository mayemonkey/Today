package com.maye.today.setting;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SettingPresenterImpl implements SettingPresenter {

    private SettingView settingView;
    private SettingModel settingModel;
    private Subscription subscribe;

    public SettingPresenterImpl(SettingView settingView) {
        this.settingView = settingView;
        settingModel = new SettingModelImpl();
    }

    @Override
    public void getUserInfo(String sessionId) {
        settingView.showProgressDialog(true, "加载", "正在加载用户信息...");

        subscribe = settingModel.getUser(sessionId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        settingView.showProgressDialog(false, "", "");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(User user) {
                        settingView.setUserData(user);
                    }
                });
    }

    @Override
    public void updateUserInfo(User user) {
        settingView.showProgressDialog(true, "上传", "正在上传用户信息");

        subscribe = settingModel.uploadUser(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        settingView.showProgressDialog(false, "", "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        settingView.refreshUserData(user);
                    }
                });
    }

    

    @Override
    public void onViewDestroy() {
        settingView = null;
        subscribe.unsubscribe();
    }

}
