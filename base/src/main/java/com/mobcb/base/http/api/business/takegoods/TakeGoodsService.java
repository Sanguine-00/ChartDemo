package com.mobcb.base.http.api.business.takegoods;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/29 0029
 * @desc : [提货管理]
 */

public interface TakeGoodsService {

    /**
     * 提货核销
     * @param object
     * @return
     */
    @PUT("api/v3/eshop/pickUp")
    Observable<ResponseBean> pickUpWriteOff(@Body Object object);

    /**
     * 根据订单号获取订单信息
     * @param orderSn
     * @return
     */
    @GET("api/v3/eshop/orders/sn/{orderSn}")
    Observable<ResponseBean> getOrderInfo(@Path("orderSn")String orderSn);

    /**
     * 获取商户列表
     * @param params
     * @return
     */
    @GET("api/v3/weibo/shop/list")
    Observable<ResponseBean> getShopList(@QueryMap Map<String, Object> params);

    /**
     * 提货核销记录接口
     * @param params
     * @return
     */
    @GET("api/v3/eshop/orders/pickUp/records")
    Observable<ResponseBean> getWriteOffRecord(@QueryMap Map<String, Object> params);

    /**
     * 提货核销获取活动列表
     * @return
     */
    @GET("api/v3/eshop/{shopId}/activity/list")
    Observable<ResponseBean> getEshopActivityList(@Path("shopId")String shopId);

}
