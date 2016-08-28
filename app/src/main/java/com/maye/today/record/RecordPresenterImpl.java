package com.maye.today.record;

import com.maye.today.domain.Record;

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
    public void showAllRecord(String username) {
        subscribe = recordModel.getRecord(username).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Record>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Record> list) {
                        recordView.showRecord(list);
                    }
                });
    }

    @Override
    public void showRecordByDay(String username, String datetime) {
        subscribe = recordModel.getRecordByDay(username, datetime).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<List<Record>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
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
        subscribe.unsubscribe();
    }

}
