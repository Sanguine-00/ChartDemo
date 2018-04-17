package com.mobcb.base.http.api.business.children;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/6/21 0021
 * @desc : [儿童手表相关接口]
 */

public interface ChildrenWatchService {

    /**
     * 绑定儿童手表
     * @param object
     * @return
     */
    @POST("/api/v3/management/childwatch/bind")
    Observable<ResponseBean> bindChildWatch(@Body Object object);

    /**
     * 儿童手表查询
     * @param params
     * @return
     */
    @GET("/api/v3/management/childwatch/info")
    Observable<ResponseBean> queryChildWatch(@QueryMap Map<String,Object> params);

    /**
     * 根据会员id查询已绑定的儿童手表信息
     * @param memberId
     * @return
     */
    @GET("/api/v3/management/member/{memberId}/childwatch")
    Observable<ResponseBean> queryBindChildWatch(@Path("memberId")String memberId);

    /**
     * 修改手表号码
     * @param memberId
     * @return
     */
    @PUT("/api/v3/management/{memberId}/childwatch/phone")
    Observable<ResponseBean> modifyWatchNumber(@Path("memberId")String memberId,@Body Map<String,Object> object);
}
