package com.mobcb.base.mvp;

import android.support.annotation.StringRes;

/**
 * Created by Sanguine on 2018/4/9.
 */

public interface BaseListener<T> {
    void onSuccess(T t);

    void onFailure(String message);

    void onFailure(@StringRes int resId);

}
