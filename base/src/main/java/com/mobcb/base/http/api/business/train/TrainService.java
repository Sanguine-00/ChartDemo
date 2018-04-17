package com.mobcb.base.http.api.business.train;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/17 0017
 * @desc : [商管/商户培训]
 */

public interface TrainService {

    //------------------------商管---------------------------
    /**
     * 根据商管培训类型id 获取商管培训列表
     * @param params
     * @return
     */
    @GET("api/v3/management/mall/training/list")
    Observable<ResponseBean> getMallTrainingList(@QueryMap Map<String,Object> params);

    /**
     * 商管培训详情
     * @param trainId
     * @param params
     * @return
     */
    @GET("api/v3/management/mall/training/{trainId}")
    Observable<ResponseBean> getMallTrainDetail(@Path("trainId")String trainId,@QueryMap Map<String,Object> params);

    /**
     * 商管培训类型列表
     * @return
     */
    @GET("api/v3/management/conf/type/mall/list")
    Observable<ResponseBean> getMallTrainTypeList();

    //-------------------------------商户------------------------------------------

    /**
     * 根据商户培训类型id 获取商户培训列表
     * @param params
     * @return
     */
    @GET("api/v3/management/shop/training/list")
    Observable<ResponseBean> getShopTrainingList(@QueryMap Map<String,Object> params);

    /**
     * 商户培训详情
     * @param trainId
     * @param params
     * @return
     */
    @GET("api/v3/management/mall/notice/{trainId}")
    Observable<ResponseBean> getShopTrainDetail(@Path("trainId")String trainId,@QueryMap Map<String,Object> params);

    /**
     * 获取商户培训类型列表
     * @return
     */
    @GET("api/v3/management/conf/type/shop/list")
    Observable<ResponseBean> getShopTrainingType();
}
