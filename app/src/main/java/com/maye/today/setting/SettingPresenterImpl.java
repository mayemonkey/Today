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
    public void checkPasswordAndUpdate(final String username, String password, final User user) {
        settingView.showProgressDialog(true, "验证密码", "正在验证用户密码");

        subscribe = settingModel.checkPassword(username, password).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                flatMap(new Func1<ResponseBody, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(ResponseBody body) {
                        settingView.showProgressDialog(true, "上传用户信息", "正在上传修改信息");
                        return settingModel.uploadUser(user);
                    }
                }).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        settingView.showProgressDialog(false, "", "");
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        settingView = null;
        subscribe.unsubscribe();
    }

}
