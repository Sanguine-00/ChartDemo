package com.mobcb.chartdemo.manager.presenter;


import android.content.Context;
import android.content.Intent;

import com.mobcb.base.helper.CacheHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.mvp.SuccessListener;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.chartdemo.manager.activity.ChangePwdActivity;
import com.mobcb.chartdemo.manager.activity.SettingActivity;
import com.mobcb.chartdemo.manager.model.SettingModel;

public class SettingPresenter extends BasePresenter<SettingActivity, SettingModel> implements CacheHelper.ClearCacheCallback {

    public SettingPresenter(SettingActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new SettingModel();
    }


    /**
     * 修改密码
     */
    public void changePwd() {
        ActivityUtils.startActivity(ChangePwdActivity.class);
    }


    /**
     * 清除缓存
     */
    public void clearCache(Context context) {
        if (mvpView != null) {
            mvpView.showLoading();
        }
        CacheHelper.clearAllCache(context, this);
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        if (mvpModel != null) {
            mvpModel.exitLogin(new SuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    //清除缓存信息,SPUtil中都清除了,DataHelper中信息也应该没有了
                    LoginHelper.getInstance().clearSP();
                    Intent intent = new Intent();
                    intent.setAction("com.mobcb.chartdemo.manager.activity.LoginActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ActivityUtils.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onClearEnd() {
        if (mvpView != null) {
            mvpView.showCacheData();
            mvpView.hideLoading();
        }
    }
}
