package com.mobcb.base.http.api.business.sign;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/6/21 0021
 * @desc : [签到，签退]
 */

public interface SignService {

    /**
     * 签到签退
     * @param object
     * @return
     */
    @POST("/api/v3/management/mall/sign")
    Observable<ResponseBean> sign(@Body Object object);

    /**
     * 获取服务器时间戳
     * @return
     */
    @GET("api/v3/management/mall/sign/timestamp")
    Observable<ResponseBean> getTimestamp();

    /**
     * 检查今日是否签到或签退
     * @param map
     * @return
     */
    @GET("api/v3/management/mall/sign/check")
    Observable<ResponseBean> checkTodaySign(@QueryMap Map<String,Object>map);

    /**
     * 获取今天的签到签退记录
     * @param map
     * @return
     */
    @GET("api/v3/management/mall/sign/record")
    Observable<ResponseBean> getTodayRecord(@QueryMap Map<String,Object>map);

    /**
     * 根据mac获取定位信息
     * @param map
     * @return
     */
    @GET("/api/v3/management/location")
    Observable<ResponseBean> getLocation(@QueryMap Map<String,Object>map);
}
