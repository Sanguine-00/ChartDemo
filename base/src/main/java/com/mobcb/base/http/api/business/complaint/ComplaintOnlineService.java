package com.mobcb.base.http.api.business.complaint;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/10 0010
 * @desc : [在线投诉]
 */

public interface ComplaintOnlineService {

    /**
     * 在线投诉类别
     * @return
     */
    @GET("api/v3/management/complaint/type")
    Observable<ResponseBean> getComplaintType();

    /**
     * 在线投诉提交
     * @param object
     * @return
     */
    @POST("api/v3/management/complaint/add")
    Observable<ResponseBean> addComplaint(@Body Object object);

    /**
     * 商户端在线投诉记录、商管端在线投诉处理列表
     * @param params
     * @return
     */
    @GET("api/v3/management/complaint/list")
    Observable<ResponseBean> getComplaintList(@QueryMap Map<String,Object> params);

    /**
     * 在线投诉详情
     * @param reportId
     * @return
     */
    @GET("api/v3/management/complaint/{reportId}")
    Observable<ResponseBean> getComplaintDetail(@Path("reportId")String reportId);

    /**
     * 商管在线投诉处理
     * @param object
     * @return
     */
    @POST("api/v3/management/complaint/handle")
    Observable<ResponseBean> handleComplaint(@Body Object object);

    /**
     * 投诉反馈
     * @param object
     * @return
     */
    @POST("api/v3/management/complaint/feedback")
    Observable<ResponseBean> feedback(@Body Object object);

    /**
     * 投诉单已读
     * @param object
     * @return
     */
    @POST("api/v3/management/commonread//{reportId}")
    Observable<ResponseBean> propertyRead(@Path("reportId")String reportId,@Body Object object);
}
