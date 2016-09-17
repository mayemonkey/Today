package com.maye.today.setting;

import com.maye.today.domain.User;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingPresenterImpl implements SettingPresenter {

    private SettingView settingView;
    private SettingModel settingModel;

    public SettingPresenterImpl(SettingView settingView) {
        this.settingView = settingView;
        settingModel = new SettingModelImpl();
    }

    @Override
    public void updateUser(User user) {
        settingView.showProgressDialog(true);

        settingModel.uploadUser(user).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        settingView.showProgressDialog(false);
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        settingView = null;

    }


}
