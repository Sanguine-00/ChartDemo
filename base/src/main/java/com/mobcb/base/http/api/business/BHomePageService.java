package com.mobcb.base.http.api.business;


import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/5 0005
 * @desc : [商管首页api]
 */

public interface BHomePageService {

    /**
     * 获取首页参数
     * @param managerId
     * @param appType
     * @return
     */
    @GET("/api/v3/managers/{managerId}/modules/{appType}")
    Observable<ResponseBean> getHomePage(@Path("managerId")String managerId, @Path("appType")String appType);
    /**
     * 根据mallId,获取门店信息
     * @param mallId
     * @return
     */
    @GET("api/v3/mall/{mallId}")
    Observable<ResponseBean> getMallInfo(@Path("mallId")String mallId);

    /**
     * 获取门店列表
     * @return
     */
    @GET("api/v3/mall/all")
    Observable<ResponseBean> getMallList();

}
