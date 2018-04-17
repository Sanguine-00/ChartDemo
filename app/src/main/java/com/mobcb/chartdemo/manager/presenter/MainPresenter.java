package com.mobcb.chartdemo.manager.presenter;

import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.mvp.SuccessListener;
import com.mobcb.base.util.NetworkUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chartdemo.manager.activity.MainActivity;
import com.mobcb.chartdemo.manager.bean.main.HomePageItem;
import com.mobcb.chartdemo.manager.model.MainModel;

import java.util.List;

/**
 * Created by Sanguine on 2018/4/10.
 */

public class MainPresenter extends BasePresenter<MainActivity, MainModel> implements BaseListener<List<HomePageItem>> {
    public MainPresenter(MainActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new MainModel();
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
            mvpModel.getRightTopUnreadCount(new SuccessListener<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    if (mvpView != null) {
                        mvpView.showUnReadCount(integer);
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(List<HomePageItem> homePageItems) {
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
