package com.mobcb.base.mvp;

/**
 * Created by Sanguine on 2018/4/11.
 */

public abstract class SuccessListener<T> implements BaseListener<T> {
    @Override
    public void onFailure(int resId) {

    }

    @Override
    public void onFailure(String message) {

    }

}
