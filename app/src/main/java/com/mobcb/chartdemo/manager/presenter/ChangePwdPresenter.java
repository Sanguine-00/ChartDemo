package com.mobcb.chartdemo.manager.presenter;


import android.content.Intent;
import android.text.TextUtils;

import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.NetworkUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.activity.ChangePwdActivity;
import com.mobcb.chartdemo.manager.model.ChangePwdModel;

public class ChangePwdPresenter extends BasePresenter<ChangePwdActivity, ChangePwdModel> implements BaseListener {

    public ChangePwdPresenter(ChangePwdActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new ChangePwdModel();
    }

    public void changePwd(String oldPwd, String newPwdOne, String newPwdTwo) {
        if (validateInput(oldPwd, newPwdOne, newPwdTwo)) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.showShort(R.string.app_no_network);
                return;
            }
            if (mvpModel != null) {
                mvpModel.changePwd(oldPwd, newPwdOne, this);
            }
        }
    }


    private boolean validateInput(String oldPwd, String newPwdOne, String newPwdTwo) {

        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showShort(R.string.app_change_pwd_null_tip_old);
            return false;
        }
        if (TextUtils.isEmpty(newPwdOne)) {
            ToastUtils.showShort(R.string.app_change_pwd_null_tip_new);
            return false;
        }
        if (TextUtils.isEmpty(newPwdTwo)) {
            ToastUtils.showShort(R.string.app_change_pwd_null_tip_new2);
            return false;
        }
        if (!newPwdOne.equals(newPwdTwo)) {
            ToastUtils.showShort(R.string.app_change_pwd_null_tip_not_same);
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(Object o) {
        if (mvpView != null) {
            mvpView.hideLoading();
            exitLogin();
        }
        ToastUtils.showShort(R.string.app_change_pwd_success);
    }

    @Override
    public void onFailure(String message) {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
        ToastUtils.showShort(message);
    }

    @Override
    public void onFailure(int resId) {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
        ToastUtils.showShort(resId);
    }

    /**
     * 修改完密码要退出登录,重新登录
     */
    public void exitLogin() {
        //清除缓存信息,SPUtil中都清除了,DataHelper中信息也应该没有了
        LoginHelper.getInstance().clearSP();
        Intent intent = new Intent();
        intent.setAction("com.mobcb.chartdemo.manager.activity.LoginActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtils.startActivity(intent);
    }
}
