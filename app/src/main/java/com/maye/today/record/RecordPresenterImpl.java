package com.maye.today.record;

import com.maye.today.domain.Record;
import com.maye.today.util.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecordPresenterImpl implements RecordPresenter {

    private RecordModel recordModel;
    private RecordView recordView;
    private Subscription subscribe;

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
                subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        recordView.showRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (recordView != null) {
                            recordView.showRefresh(false);
                            recordView.showRecordCount("");
                            ToastUtil.showShortToast("获取Record数量失败");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        recordView.showRecordCount(s);
                    }
                });
    }

    @Override
    public void showAllRecord(String username, int start) {
        recordView.showRefresh(true);

        subscribe = recordModel.getRecord(username, start).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Record>>() {
                    @Override
                    public void onCompleted() {
                        recordView.showRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (recordView != null) {
                            recordView.showRefresh(false);
                            recordView.showRecord(null);
                            ToastUtil.showShortToast("加载Record失败");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Record> list) {
                        recordView.showRecord(list);
                    }
                });
    }

    @Override
    public void showRecordByDay(String username, String datetime) {
        recordView.showRefresh(true);

        subscribe = recordModel.getRecordByDay(username, datetime).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Record>>() {
                    @Override
                    public void onCompleted() {
                        recordView.showRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (recordView != null) {
                            recordView.showRefresh(false);
                            recordView.showRecord(null);
                            ToastUtil.showShortToast("加载Record失败");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Record> list) {
                        recordView.showRecord(list);
                    }
                });
    }

    @Override
    public void showRecordByAssignTime(String username, int type, String datetime, int start) {
        subscribe = recordModel.getRecordByAssignTime(username, type, datetime, 0).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Record>>() {
                    @Override
                    public void onCompleted() {
                        recordView.showRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (recordView != null) {
                            recordView.showRefresh(false);
                            recordView.showRecord(null);
                            ToastUtil.showShortToast("加载Record失败");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Record> list) {
                        recordView.showRecord(list);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        recordView = null;
        if (subscribe != null)
            subscribe.unsubscribe();
    }

}
