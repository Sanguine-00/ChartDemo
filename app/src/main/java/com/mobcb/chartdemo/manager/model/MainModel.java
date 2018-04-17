package com.mobcb.chartdemo.manager.model;

import android.text.TextUtils;

import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.api.business.BHomePageService;
import com.mobcb.base.http.api.business.notice.SystemNoticeService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BaseModel;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.bean.main.HomePageItem;
import com.mobcb.chartdemo.manager.bean.main.MsgCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sanguine on 2018/4/10.
 */

public class MainModel extends BaseModel {

    public void requestData(final BaseListener<List<HomePageItem>> listener) {
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
                                    listener.onFailure(R.string.app_server_error);
                                }
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean == null) {
                                    if (listener != null) {
                                        listener.onFailure(R.string.app_server_error);
                                    }
                                }
                                if (ApiUtils.checkCode(responseBean)) {
                                    List<HomePageItem> homePageItems = ApiUtils.getDataList(responseBean, HomePageItem.class);
                                    if (listener != null) {
                                        listener.onSuccess(homePageItems);
                                    }
                                } else if (responseBean.getDisplay() && TextUtils.isEmpty(responseBean.getErrorMessage())) {
                                    if (listener != null) {
                                        listener.onFailure(responseBean.getErrorMessage());
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onFailure(R.string.app_server_error);
                                    }
                                }
                            }
                        });
            }
        });
    }


    /**
     * 获取右上角未读消息数
     */
    public void getRightTopUnreadCount(final BaseListener<Integer> listener) {
        AuthHelper.auth(SystemNoticeService.class).subscribeOn(Schedulers.io()).subscribe(new Action1<SystemNoticeService>() {
            @Override
            public void call(SystemNoticeService systemNoticeService) {
                Map<String, Object> map = new HashMap<>(0);
                map.put("managerId", LoginHelper.getInstance().getManagerId());
                map.put("type", "");
                Observable<ResponseBean> observable = systemNoticeService.getNoticeCount(map);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean != null) {
                                    if (ApiUtils.checkCode(responseBean)) {
                                        MsgCount msgCount = ApiUtils.getData(responseBean, MsgCount.class);
                                        Integer unreadMsgCount = 0;
                                        if (msgCount != null) {
                                            unreadMsgCount = msgCount.getUnreadCount();
                                        }
                                        if (listener != null) {
                                            listener.onSuccess(unreadMsgCount);
                                        }
                                    }
                                }
                            }
                        });
            }
        });

    }
}
