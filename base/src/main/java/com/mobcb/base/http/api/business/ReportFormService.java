package com.mobcb.base.http.api.business;

import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/28 0028
 * @desc : [相关类/方法]
 */

public interface ReportFormService {

    /**
     * 近一个月的微信粉丝增长情况
     * @return
     */
    @GET("api/v3/admin/mall/manager/kpi/wxmemberadd")
    Observable<ResponseBean> getWXMemberFans();

    /**
     * 获取已注册会员数
     * @return
     */
    @GET("api/v3/admin/mall/manager/kpi/registmemberadd")
    Observable<ResponseBean> getRegisterMembers();

    /**
     * 获取微信粉丝总数和注册会员总数
     * @return
     */
    @GET("api/v3/admin/mall/manager/kpi/wxmember")
    Observable<ResponseBean> getWXAndVipMembers();
}
