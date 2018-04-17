package com.mobcb.base.http.api.business.propertyrepair;

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
 * @date : 2017/7/6 0006
 * @desc : [物业报修]
 */

public interface PropertyRepairService {

    /**
     * 物业报修类型列表
     *
     * @return
     */
    @GET("api/v3/management/property/type")
    Observable<ResponseBean> getPropertyType();

    /**
     * 商管端物业报修处理列表
     *
     * @param params
     * @return
     */
    @GET("api/v3/management/property/list")
    Observable<ResponseBean> getPropertyList(@QueryMap Map<String, Object> params);

    /**
     * 报修处理
     *
     * @param object
     * @return
     */
    @POST("/api/v3/management/property/handle")
    Observable<ResponseBean> handle(@Body Object object);

    /**
     * 商户端物业报修、商家端现场管理
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/add")
    Observable<ResponseBean> add(@Body Object object);

    /**
     * 报修详情
     *
     * @param reportId
     * @return
     */
    @GET("api/v3/management/property/{reportId}")
    Observable<ResponseBean> getDetail(@Path("reportId") String reportId);

    /**
     * 评价
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/comment")
    Observable<ResponseBean> comment(@Body Object object);

    /**
     * 认领
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/claim")
    Observable<ResponseBean> claim(@Body Object object);

    /**
     * 反馈
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/feedback")
    Observable<ResponseBean> feedback(@Body Object object);

    /**
     * 转移
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/transfer")
    Observable<ResponseBean> transfer(@Body Object object);


    /**
     * 获取部门列表
     *
     * @return
     */
    @GET("api/v3/management/department/list")
    Observable<ResponseBean> getDepartmentList();

    /**
     * 报修单已读
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/commonread/{reportId}")
    Observable<ResponseBean> propertyRead(@Path("reportId") String reportId, @Body Object object);

    /**
     * 评价保修单
     *
     * @param object
     * @return
     */
    @POST("api/v3/management/property/comment")
    Observable<ResponseBean> commentProperty(@Body Object object);
}
