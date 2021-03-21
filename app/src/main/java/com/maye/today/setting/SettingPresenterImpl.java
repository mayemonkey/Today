package com.maye.today.setting;

import com.maye.today.domain.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingPresenterImpl implements SettingPresenter {

    private SettingView settingView;
    private SettingModel settingModel;
    private Disposable subscribe;

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
                subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Throwable {
                        settingView.setUserData(user);
                        settingView.showProgressDialog(false, "", "");
                    }
                });
    }

    @Override
    public void updateUserInfo(User user) {
        settingView.showProgressDialog(true, "上传", "正在上传用户信息");

        subscribe = settingModel.uploadUser(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Throwable {
                        settingView.refreshUserData(user);
                        settingView.showProgressDialog(false, "", "");
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        settingView = null;
        if (subscribe != null)
            subscribe.dispose();
    }

}
