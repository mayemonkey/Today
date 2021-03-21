package com.maye.today.record;

import com.maye.today.util.ToastUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecordPresenterImpl implements RecordPresenter {

    private RecordModel recordModel;
    private RecordView recordView;
    private Disposable subscribe;

    public RecordPresenterImpl(RecordView recordView) {
        this.recordModel = new RecordModelImpl();
        this.recordView = recordView;
    }

    @Override
    public void showRecordCount(String username) {
        recordView.showRefresh(true);

        subscribe = recordModel.getRecordCount(username).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(s -> recordView.showRecordCount(s), throwable -> {
                    if (recordView != null) {
                        recordView.showRefresh(false);
                        recordView.showRecordCount("");
                        ToastUtil.showShortToast("获取Record数量失败");
                    }
                    throwable.printStackTrace();
                }, () -> recordView.showRefresh(false));
    }

    @Override
    public void showAllRecord(String username, int start) {
        recordView.showRefresh(true);

        subscribe = recordModel.getRecord(username, start).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(records -> recordView.showRecord(records), throwable -> {
                    if (recordView != null) {
                        recordView.showRefresh(false);
                        recordView.showRecord(null);
                        ToastUtil.showShortToast("加载Record失败");
                    }
                    throwable.printStackTrace();
                }, () -> recordView.showRefresh(false));
    }

    @Override
    public void showRecordByDay(String username, String datetime) {
        recordView.showRefresh(true);

        subscribe = recordModel.getRecordByDay(username, datetime).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(s-> recordView.showRecord(s), throwable -> {
                    if (recordView != null) {
                        recordView.showRefresh(false);
                        recordView.showRecord(null);
                        ToastUtil.showShortToast("加载Record失败");
                    }
                    throwable.printStackTrace();
                }, ()-> recordView.showRefresh(false));
    }

    @Override
    public void showRecordByAssignTime(String username, int type, String datetime, int start) {
        subscribe = recordModel.getRecordByAssignTime(username, type, datetime, 0).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(s-> recordView.showRecord(s), throwable -> {
                    if (recordView != null) {
                        recordView.showRefresh(false);
                        recordView.showRecord(null);
                        ToastUtil.showShortToast("加载Record失败");
                    }
                    throwable.printStackTrace();
                }, () -> recordView.showRefresh(false));
    }

    @Override
    public void onDestroyView() {
        recordView = null;
        if (subscribe != null)
            subscribe.dispose();
    }

}
