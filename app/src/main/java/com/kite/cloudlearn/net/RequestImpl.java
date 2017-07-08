package com.kite.cloudlearn.net;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2017/7/5.
 */

public interface RequestImpl {

    void loadSuccess(Object object);

    void loadFailed();

    void addDisposable(Disposable disposable);
}
