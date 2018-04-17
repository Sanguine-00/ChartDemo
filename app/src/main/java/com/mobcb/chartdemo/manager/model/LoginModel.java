package com.mobcb.chartdemo.manager.model;

import com.mobcb.base.http.api.business.BusinessMemberService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.bean.LoginResponseInfo;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sanguine on 2018/4/9.
 */

public class LoginModel extends BaseModel {

    /**
     * 请求登录接口
     *
     * @param mActivity
     * @param username
     * @param password
     * @param captcha
     * @param captchaId
     * @param listener
     */
    public void loginRequest(final String username,
                             final String password,
                             final String captcha,
                             final String captchaId,
                             final String deviceId,
                             final BaseListener<LoginResponseInfo> listener) {
        AuthHelper.auth(BusinessMemberService.class).subscribeOn(Schedulers.io()).subscribe(new Action1<BusinessMemberService>() {
            @Override
            public void call(BusinessMemberService businessMemberService) {
                Map<String, Object> params = new HashMap<>();
                params.put("captchaId", captchaId);
                params.put("captcha", captcha);
                params.put("username", username);
                params.put("password", password);
                params.put("deviceId", deviceId);
                Observable<ResponseBean> observable = businessMemberService.managerLogin(params);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                if (listener != null) {
                                    listener.onFailure(R.string.app_login_failed);
                                }
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean != null) {
                                    if (ApiUtils.checkCode(responseBean)) {
                                        LoginResponseInfo responseInfo = ApiUtils.getData(responseBean, LoginResponseInfo.class);
                                        if (listener != null) {
                                            listener.onSuccess(responseInfo);
                                        }
                                    } else {
                                        if (responseBean.getDisplay() && responseBean.getErrorMessage() != null) {
                                            if (listener != null) {
                                                listener.onFailure(responseBean.getErrorMessage());
                                            }
                                        } else if (listener != null) {
                                            listener.onFailure(R.string.app_login_failed);
                                        }
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onFailure(R.string.app_login_failed);
                                    }
                                }
                            }
                        });
            }
        });
    }
}
