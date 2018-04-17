package com.mobcb.chartdemo.manager.presenter;

import android.Manifest;
import android.app.Activity;

import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.callback.AuthCallback;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.NetworkUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.activity.LoginActivity;
import com.mobcb.chartdemo.manager.activity.MainActivity;
import com.mobcb.chartdemo.manager.activity.StartActivity;
import com.mobcb.chartdemo.manager.model.StartModel;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 * Created by Sanguine on 2018/4/9.
 */

public class StartPresenter extends BasePresenter<StartActivity, StartModel> {


    private Activity mActivity;
    private AuthCallback mAuthCallBack = new AuthCallback() {
        @Override
        public void onSuccess() {
            if (LoginHelper.getInstance().isLogin() && LoginHelper.getInstance().checkSession()) {
                ActivityUtils.startActivity(MainActivity.class);
            } else {
                ActivityUtils.startActivity(LoginActivity.class);
            }
            closeCurrentPage();
        }

        @Override
        public void onFailure() {
            ToastUtils.showShort(R.string.app_register_device_failed);
            closeCurrentPage();
        }
    };

    public StartPresenter(StartActivity mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new StartModel();
        this.mActivity = mvpView;
        checkPermission();
    }

    private void checkPermission() {
        Acp.getInstance(mActivity).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.READ_SMS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        registerDevice();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        ToastUtils.showShort(R.string.app_permission_failed);
                        closeCurrentPage();
                    }
                }
        );
    }

    /**
     * 注册设备
     */
    private void registerDevice() {
        if (!NetworkUtils.isConnected()) {
            if (mvpView != null) {
                mvpView.networkTip();
            }
            return;
        }
        if (mvpModel != null) {
            mvpModel.initAuth(mAuthCallBack);
        }
    }

    /**
     * 关闭当前页面
     */
    public void closeCurrentPage() {
        if (mvpView != null) {
            mvpView.finish();
        }
    }
}
