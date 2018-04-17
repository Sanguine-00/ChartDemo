package com.mobcb.chartdemo.manager.model;


import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.api.business.BusinessMemberService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.security.SHA;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.chartdemo.manager.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ChangePwdModel extends BaseModel {

    /**
     * 调用修改密码的接口
     *
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @param listener 回调
     */
    public void changePwd(final String oldPwd, final String newPwd, final BaseListener listener) {
        AuthHelper.auth(BusinessMemberService.class).subscribeOn(Schedulers.io()).subscribe(new Action1<BusinessMemberService>() {
            @Override
            public void call(BusinessMemberService businessMemberService) {
                String managerId = LoginHelper.getInstance().getManagerId();
                Observable<ResponseBean> observable = businessMemberService.modifyPassword(managerId, getJSON(managerId, oldPwd, newPwd));
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                if (listener != null) {
                                    listener.onFailure(R.string.app_server_error);
                                }
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean == null) {
                                    if (listener != null) {
                                        listener.onFailure(R.string.app_server_error);
                                    }
                                    return;
                                }
                                if (ApiUtils.checkCode(responseBean)) {
                                    if (listener != null) {
                                        listener.onSuccess(null);
                                    }
                                } else if (responseBean.getDisplay() && !TextUtils.isEmpty(responseBean.getErrorMessage())) {
                                    if (listener != null) {
                                        listener.onFailure(responseBean.getErrorMessage());
                                    }
                                } else if (listener != null) {
                                    listener.onFailure(R.string.app_server_error);
                                }
                            }
                        });
            }
        });
    }

    /**
     * 拼接json数据
     *
     * @param managerId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    private JsonObject getJSON(String managerId, String oldPwd, String newPwd) {
        JsonObject person = new JsonObject();
        person.addProperty("managerId", managerId);
        person.addProperty("oldPassword", SHA.encryptToDES(oldPwd, "MOBCB123"));
        person.addProperty("newPassword", SHA.encryptToDES(newPwd, "MOBCB123"));
        return person;
    }
}
