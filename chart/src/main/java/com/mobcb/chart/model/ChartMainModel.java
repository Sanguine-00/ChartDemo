package com.mobcb.chart.model;


import android.text.TextUtils;

import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.api.business.BHomePageService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartHomePageItem;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ChartMainModel extends BaseModel {
    public void requestData(final BaseListener<List<ChartHomePageItem>> listener) {
        AuthHelper.auth(BHomePageService.class).subscribeOn(Schedulers.io()).subscribe(new Action1<BHomePageService>() {
            @Override
            public void call(BHomePageService bHomePageService) {
                String managerId = LoginHelper.getInstance().getManagerId();
                String appType = "management_mall";
                Observable<ResponseBean> observable = bHomePageService.getHomePage(managerId, appType);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                if (listener != null) {
                                    listener.onFailure(R.string.chart_server_error);
                                }
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean == null) {
                                    if (listener != null) {
                                        listener.onFailure(R.string.chart_server_error);
                                    }
                                }
                                if (ApiUtils.checkCode(responseBean)) {
                                    List<ChartHomePageItem> homePageItems = ApiUtils.getDataList(responseBean, ChartHomePageItem.class);
                                    if (listener != null) {
                                        listener.onSuccess(homePageItems);
                                    }
                                } else if (responseBean.getDisplay() && TextUtils.isEmpty(responseBean.getErrorMessage())) {
                                    if (listener != null) {
                                        listener.onFailure(responseBean.getErrorMessage());
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onFailure(R.string.chart_server_error);
                                    }
                                }
                            }
                        });
            }
        });
    }
}
