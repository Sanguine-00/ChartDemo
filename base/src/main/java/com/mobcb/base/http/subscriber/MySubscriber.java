package com.mobcb.base.http.subscriber;

import com.mobcb.base.util.LogUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by lvmenghui
 * on 2017/4/21.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            LogUtils.e(e.getMessage());
        } else if (e instanceof SocketTimeoutException) {
            LogUtils.e(e.getMessage());
        } else if (e instanceof ClassCastException) {
            LogUtils.e(e.getMessage());
        } else if (e instanceof java.net.ConnectException) {
            LogUtils.e(e.getMessage());
        } else {
            LogUtils.e(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {

    }
}
