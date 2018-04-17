package com.mobcb.chartdemo.manager.model;

import com.mobcb.base.http.callback.AuthCallback;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.base.util.NetworkUtils;

/**
 * Created by Sanguine on 2018/4/9.
 */

public class StartModel extends BaseModel {

    /**
     * 初始化auth服务
     */
    public void initAuth(final AuthCallback authCallback) {
        if (NetworkUtils.isConnected()) {
            AuthHelper.initAuth(3, authCallback);
        }
    }

}
