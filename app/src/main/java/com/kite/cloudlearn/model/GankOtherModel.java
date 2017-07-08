package com.kite.cloudlearn.model;

import com.kite.cloudlearn.bean.GankIoDataBean;
import com.kite.cloudlearn.http.HttpClient;
import com.kite.cloudlearn.net.RequestImpl;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/7/5.
 */

public class GankOtherModel {

    private String id;
    private int page;
    private int per_page;

    public void setData(String id, int page, int per_page) {
        this.id = id;
        this.page = page;
        this.per_page = per_page;
    }

    public void getGankIoData(final RequestImpl listener) {
        Disposable disposable = HttpClient.Builder.getGankIoServer()
                .getGankIoData(id, page, per_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankIoDataBean>() {
                    @Override
                    public void onNext(@NonNull GankIoDataBean gankIoDataBean) {
                        listener.loadSuccess(gankIoDataBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        listener.addDisposable(disposable);

    }
}
