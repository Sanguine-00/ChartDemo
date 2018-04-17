package com.mobcb.base.http.api.business.patrol;

import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/8/1 0001
 * @desc : [后场巡更接口]
 */

public interface PatrolService {

    /**
     * 获取巡更计划
     * @param managerId
     * @return
     */
    @GET("api/v3/management/{managerId}/patrols/schedules")
    Observable<ResponseBean> getSchedules(@Path("managerId")String managerId);

    /**
     * 新增巡更记录
     * @param schedulingId
     * @param recordId
     * @return
     */
    @POST("api/v3/management/patrols/schedules/{schedulingId}/records/{recordId}")
    Observable<ResponseBean> addPatrolRecord(@Path("schedulingId")String schedulingId,@Path("recordId")String recordId);
}
