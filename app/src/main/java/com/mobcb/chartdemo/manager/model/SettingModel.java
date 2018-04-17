package com.mobcb.chartdemo.manager.model;


import com.google.gson.JsonObject;
import com.mobcb.base.helper.DataHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.api.business.BusinessMemberService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.base.mvp.SuccessListener;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SettingModel extends BaseModel {
    /**
     * 退出登录
     *
     * @param listener
     */
    public void exitLogin(final SuccessListener listener) {
        final String managerId = LoginHelper.getInstance().getManagerId();
        final String deviceId = DataHelper.getInstance().getDeviceId();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("managerId", managerId);
        jsonObject.addProperty("deviceId", deviceId);

        AuthHelper.auth(BusinessMemberService.class).subscribeOn(Schedulers.io()).subscribe(new Action1<BusinessMemberService>() {
            @Override
            public void call(final BusinessMemberService memberService) {
                Observable<ResponseBean> observable = memberService.memberLoginOut(jsonObject);
                observable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                if (listener != null) {
                                    listener.onSuccess(null);
                                }
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (listener != null) {
                                    listener.onSuccess(null);
                                }
                            }
                        });
            }
        });
    }
}
