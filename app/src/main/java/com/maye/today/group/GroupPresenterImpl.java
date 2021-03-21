package com.maye.today.group;

import com.maye.today.domain.Group;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
public class GroupPresenterImpl implements GroupPresenter {

    private GroupModel groupModel;
    private GroupView groupView;
    private Disposable update;
    private Disposable remove;
    private Disposable load;
    private Disposable add;

    public GroupPresenterImpl(GroupView groupView) {
        this.groupModel = new GroupModelImpl();
        this.groupView = groupView;
    }

    @Override
    public void loadGroup(String date, String type) {
        Disposable load = groupModel.getGroup(date, type).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<Group>>() {

                    @Override
                    public void accept(List<Group> groups) throws Throwable {
                        groupView.showGroup(groups);
                    }
                });
    }

    @Override
    public void addGroup(final Group group) {
        add = groupModel.addGroup(group).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        if (true) {
                            groupView.addItem(group);
                            groupView.showToast("item added");
                        } else {
                            groupView.showToast("failed to add");
                        }
                    }
                });
    }

    @Override
    public void removeGroup(int id, final int position) {
        remove = groupModel.removeGroup(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        if (true) {
                            groupView.removeItem(position);
                            groupView.showToast("remove");
                        } else {
                            groupView.showToast("failed to remove");
                        }
                    }
                });
    }

    @Override
    public void updateGroup(List<Group> groups) {
        update = groupModel.updateGroup(groups).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        if (true) {
                            groupView.showToast("");
                            groupView.finishActivity();
                        } else {
                            groupView.showToast("");
                        }
                    }
                });
    }

    @Override
    public void viewDestroy() {
        groupView = null;
        if (remove != null)
            remove.dispose();

        if (update != null)
            update.dispose();

        if (load != null)
            load.dispose();
    }
}