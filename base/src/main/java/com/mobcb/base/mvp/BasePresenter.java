package com.mobcb.base.mvp;

public abstract class BasePresenter<V extends BaseMvpView, M extends BaseModel> {
    protected V mvpView;
    protected M mvpModel;

    public void destory() {
        mvpView = null;
        mvpModel.cancel();
        mvpModel = null;
    }
}