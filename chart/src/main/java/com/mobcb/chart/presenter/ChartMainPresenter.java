package com.mobcb.chart.presenter;


import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.util.NetworkUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chart.activity.ChartMainActivity;
import com.mobcb.chart.bean.ChartHomePageItem;
import com.mobcb.chart.model.ChartMainModel;

import java.util.List;

public class ChartMainPresenter extends BasePresenter<ChartMainActivity, ChartMainModel> implements BaseListener<List<ChartHomePageItem>> {

    public ChartMainPresenter(ChartMainActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new ChartMainModel();
        requestData(true);
    }

    public void requestData(boolean needRefresh) {
        if (!NetworkUtils.isConnected()) {
            if (mvpView != null) {
                mvpView.endRefresh();
                mvpView.hideLoading();
            }
            return;
        }
        if (needRefresh && mvpView != null) {
            mvpView.showLoading();
        }

        if (mvpModel != null) {
            mvpModel.requestData(this);
        }
    }

    @Override
    public void onSuccess(List<ChartHomePageItem> homePageItems) {
        if (mvpView != null) {
            mvpView.hideLoading();
            mvpView.endRefresh();
            mvpView.showData(homePageItems);
        }
    }

    @Override
    public void onFailure(String message) {
        if (mvpView != null) {
            mvpView.hideLoading();
            mvpView.endRefresh();
        }
        ToastUtils.showShort(message);

    }

    @Override
    public void onFailure(int resId) {
        if (mvpView != null) {
            mvpView.hideLoading();
            mvpView.endRefresh();
        }
        ToastUtils.showShort(resId);
    }
}
