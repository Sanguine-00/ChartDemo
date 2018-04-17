package com.mobcb.chartdemo.manager.presenter;


import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.chartdemo.manager.activity.SystemMessageActivity;
import com.mobcb.chartdemo.manager.model.SystemMessageModel;

public class SystemMessagePresenter extends BasePresenter<SystemMessageActivity, SystemMessageModel> {

    public SystemMessagePresenter(SystemMessageActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new SystemMessageModel();
    }
}
